# Tenant-Core Library

![Java](https://img.shields.io/badge/language-Java21-blue) ![SpringBoot](https://img.shields.io/badge/spring--boot-3.5-green) ![PostgreSQL](https://img.shields.io/badge/database-PostgreSQL-blue) ![Version](https://img.shields.io/badge/version-1.0.0-yellow)

## Overview

**tenant-core** is a shared Java library designed to provide multi-tenancy support with **schema-per-tenant** architecture for Spring Boot microservices. This library eliminates code duplication across microservices by centralizing tenant management, database schema switching, and HTTP request interception.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Component Overview](#component-overview)
- [Configuration](#configuration)
- [API Reference](#api-reference)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)


## Features

- ✅ **Schema-per-tenant** multi-tenancy support
- ✅ **Thread-safe** tenant context management
- ✅ **Automatic** tenant resolution from HTTP headers
- ✅ **Hibernate integration** with multi-tenant connection provider
- ✅ **Dynamic schema creation** and validation
- ✅ **Spring Boot** auto-configuration support
- ✅ **PostgreSQL** optimized (extensible to other databases)

## Architecture

### High-Level Architecture Diagram

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   HTTP Request  │───▶│  TenantInterceptor │───▶│ TenantResolver  │
│ (X-Tenant-ID)   │    │                  │    │                 │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │                         │
                                ▼                         ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │  TenantContext  │    │ Extract Tenant  │
                       │ (ThreadLocal)   │    │      ID         │
                       └─────────────────┘    └─────────────────┘
                                │
                                ▼
                    ┌──────────────────────────┐
                    │    Hibernate Session     │
                    │                          │
                    └──────────────────────────┘
                                │
                                ▼
                    ┌──────────────────────────┐
                    │  ConnectionProvider      │
                    │  - Schema Validation     │
                    │  - Connection Switching  │
                    └──────────────────────────┘
                                │
                                ▼
                    ┌──────────────────────────┐
                    │    PostgreSQL DB         │
                    │  ┌─────────────────────┐ │
                    │  │ public (default)    │ │
                    │  │ tenant_a            │ │
                    │  │ tenant_b            │ │
                    │  │ tenant_c            │ │
                    │  └─────────────────────┘ │
                    └──────────────────────────┘
```

### Component Flow Diagram

```
Request Flow:
1. HTTP Request → TenantInterceptor.preHandle()
2. TenantInterceptor → HttpHeaderTenantResolver.resolveTenantId()
3. TenantResolver → TenantContext.setCurrentTenant()
4. Database Query → TenantIdentifierResolver.resolveCurrentTenantIdentifier()
5. Hibernate → ConnectionProvider.getConnection()
6. ConnectionProvider → Schema Validation & Switch
7. Response Complete → TenantInterceptor.postHandle() → TenantContext.removeCurrentTenant()
```

## Installation

### Maven

xml

```xml
<dependency>
    <groupId>com.lms</groupId>
    <artifactId>tenant-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

gradle

```gradle
implementation 'com.lms:tenant-core:1.0.0'
```

## Quick Start



### 1. Extend AbstractMultiTenantJpaConfig

If you want to enable the Multi-tenancy schema per tenant in your other microservices

you have to add this in your main class as well

**Example**: adding this library in my ***UserManagementService***
``` java
@SpringBootApplication  
@ComponentScan(basePackages = {  
       "com.lms.usermanagementservice",  
       "com.lms.tenantcore"  
})  
@EnableDiscoveryClient  
public class UserManagementServiceApplication {  
  
    public static void main(String[] args) {  
       SpringApplication.run(UserManagementServiceApplication.class, args);  
    }  
}
```


```java
  
@Configuration  
@EnableJpaRepositories(  
        basePackages = "com.lms.usermanagementservice.Repository",  
        entityManagerFactoryRef = "entityManagerFactory",  
        transactionManagerRef = "transactionManager"  
)  
public class UserManagementJpaConfig extends AbstractMultiTenantJpaConfig {  
  
    @Override  
    protected String[] getEntityPackages() {  
        return new String[] { "com.lms.usermanagementservice.Model" };  // model directory
    }  
    @Override  
    protected String getPersistenceUnitName() {  
        return "user-management";  
    }}
```


### 2. Create Tenant Schemas

#### **TenantSchemaInitializer** 
``` TenantSchemaInitializer.java

This class is there in this package so that it can be called by other services to create schema dynamically

```

java

```java
@Service
public class TenantManagementService {
    
    @Autowired
    private TenantSchemaInitializer schemaInitializer;
    
    public void createTenant(String tenantId) {
        schemaInitializer.createTenantSchema(tenantId);
    }
}
```

### 3. Make HTTP Requests with Tenant Header

bash

```bash
curl -H "X-Tenant-ID: tenant_abc" http://localhost:8080/api/users
```

## Component Overview

### Folder Structure

``` package structure
com.lms.tenantcore
│
├── TenantContext.java                 # Thread-local tenant storage
├── TenantInterceptor.java            # Interceptor to extract tenant from request
├── TenantSchemaInitializer.java      # Schema creation logic, Allows other services to dynamically create schemas
│
├── Config/
│   ├── AbstractMultiTenantJpaConfig.java   # Base JPA config for multi-tenancy
│   └── WebConfig.java                     # Registers the interceptor
│
├── HibernateConfig/
│   ├── ConnectionProvider.java      # Provides schema-switched DB connection
│   └── TenantIdentifierResolver.java # Resolves tenant for Hibernate context
│
└── Resolver/
    ├── HttpHeaderTenantResolver.java   # Extracts tenant ID from HTTP headers
    └── TenantResolver.java             # Interface for tenant resolution

```

### Core Components

|Component|Purpose|Key Methods|
|---|---|---|
|`TenantContext`|Thread-local tenant storage|`setCurrentTenant()`, `getCurrentTenant()`, `removeCurrentTenant()`|
|`TenantInterceptor`|HTTP request interception|`preHandle()`, `postHandle()`, `afterCompletion()`|
|`TenantSchemaInitializer`|Schema creation and management|`createTenantSchema()`|

### Configuration Components

|Component|Purpose|Key Methods|
|---|---|---|
|`AbstractMultiTenantJpaConfig`|JPA/Hibernate configuration base|`entityManagerFactory()`, `transactionManager()`|
|`WebConfig`|Spring MVC interceptor registration|`addInterceptors()`|

### Hibernate Integration

|Component|Purpose|Key Methods|
|---|---|---|
|`ConnectionProvider`|Multi-tenant connection management|`getConnection()`, `releaseConnection()`|
|`TenantIdentifierResolver`|Current tenant identification|`resolveCurrentTenantIdentifier()`|

### Tenant Resolution

|Component|Purpose|Key Methods|
|---|---|---|
|`HttpHeaderTenantResolver`|Extract tenant from HTTP headers|`resolveTenantId()`|
|`TenantResolver`|Generic tenant resolution interface|`resolveTenantId()`|

## Configuration

### Required Dependencies

Your microservice must include these dependencies:

xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
	<dependency>  
	    <groupId>org.projectlombok</groupId>  
	    <artifactId>lombok</artifactId>  
	    <optional>true</optional>  
	</dependency>
    
</dependencies>
```


| Dependecy artifact-id        | Use case                                                                                                                                                                                 |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| spring-boot-starter-data-jpa | Used for multi-tenancy configuration with Hibernate, enabling schema-based tenant separation through `MultiTenantConnectionProvider`, `TenantIdentifierResolver`, and JPA configuration. |
| spring-boot-starter-web      | Used for intercepting incoming HTTP requests via `TenantInterceptor` and extracting tenant headers using `HttpHeaderTenantResolver`.                                                     |
| lombok                       | Extensively used in classes like `TenantInterceptor`, `ConnectionProvider`, and `WebConfig` to generate constructors and simplify model classes.                                         |

### Environment-Specific Configuration

#### Development

properties

```properties
# Enable SQL logging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
```

#### Production

properties

```properties
# Optimize connection pooling
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

## API Reference

### TenantContext

Thread-local storage for current tenant information.

java

```java
// Set current tenant (automatically done by interceptor)
TenantContext.setCurrentTenant("tenant_abc");

// Get current tenant
String currentTenant = TenantContext.getCurrentTenant();

// Remove current tenant (automatically done by interceptor)
TenantContext.removeCurrentTenant();
```

### TenantSchemaInitializer

Utility for creating tenant schemas.

java

```java
@Autowired
private TenantSchemaInitializer schemaInitializer;

// Create new tenant schema
schemaInitializer.createTenantSchema("new_tenant");
```

## Best Practices

### 1. Tenant Naming Convention

- Use lowercase tenant IDs
- Use underscores instead of hyphens
- Keep tenant IDs short and meaningful
- Example: `tenant_abc`, `company_xyz`

### 2. Error Handling

java

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TenantNotFoundExceptions.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFound(TenantNotFoundExceptions ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("TENANT_NOT_FOUND", ex.getMessage()));
    }
}
```

### 3. Testing

java

```java
@Test
public void testMultiTenantBehavior() {
    // Set tenant context for testing
    TenantContext.setCurrentTenant("test_tenant");
    
    try {
        // Your test logic here
        List<User> users = userService.getAllUsers();
        // Assertions
    } finally {
        // Clean up
        TenantContext.removeCurrentTenant();
    }
}
```

### 4. Schema Migration

- Use Flyway or Liquibase for schema versioning
- Apply migrations to all tenant schemas
- Maintain schema consistency across tenants

### 5. Monitoring and Logging

- Log tenant context in all operations
- Monitor database connections per tenant
- Track schema creation and access patterns

## Troubleshooting

### Common Issues

#### 1. TenantNotFoundExceptions

**Cause**: Schema doesn't exist for the specified tenant **Solution**:

java

```java
// Create schema before use
schemaInitializer.createTenantSchema("your_tenant");
```

#### 2. Connection Pool Exhaustion

**Cause**: Too many concurrent tenant connections **Solution**: Optimize connection pool settings

properties

```properties
spring.datasource.hikari.maximum-pool-size=30
spring.datasource.hikari.leak-detection-threshold=60000
```

#### 3. Schema Not Switching

**Cause**: Tenant context not properly set **Solution**: Ensure interceptor is properly configured and tenant header is present

### Debug Configuration

properties

```properties
# Enable debug logging
logging.level.com.lms.tenantcore=DEBUG
logging.level.org.hibernate.engine.jdbc.connections=DEBUG
```




---

**Version**: 1.0.0  
**Last Updated**: 2025-07-28  
**Maintainers**: Akshay Kumar Pandit