# OpenAPI definition
**Version:** v0

---

##  Servers
* `http://localhost:8082`

---

## 🔑 Global Headers
All endpoints require a mandatory header for multi-tenancy:

* **X-Tenant-Id** (header, **required**): Required ID of the tenant for multi-tenancy context.

---

# 🏷️ API Endpoints

## 🌍 Global Actions API
*APIs to manage global actions in the system*

### `GET /api/v1/global/actions`
**Summary:** Get all global actions
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: List of actions retrieved successfully. (Returns `ActionResponseDTO[]`)

### `POST /api/v1/global/actions`
**Summary:** Create a new global action
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `ActionRequestDTO`
* **Responses:**
    * `201 Created`: Action created successfully. (Returns `ActionResponseDTO`)
    * `400 Bad Request`: Invalid request data. (Returns `ActionResponseDTO`)

### `GET /api/v1/global/actions/{id}`
**Summary:** Get global action by ID
* **Parameters:**
    * `id` (path, **required**): ID of the action to retrieve (uuid).
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: Action retrieved successfully. (Returns `ActionResponseDTO`)
    * `404 Not Found`: Action not found. (Returns `ActionResponseDTO`)

### `PUT /api/v1/global/actions/{id}`
**Summary:** Update an existing global action by ID
* **Parameters:**
    * `id` (path, **required**): ID of the action to update (uuid).
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `ActionRequestDTO`
* **Responses:**
    * `200 OK`: Action updated successfully. (Returns `ActionResponseDTO`)
    * `404 Not Found`: Action not found. (Returns `ActionResponseDTO`)

### `DELETE /api/v1/global/actions/{id}`
**Summary:** Delete a global action by ID
* **Parameters:**
    * `id` (path, **required**): ID of the action to delete (uuid).
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `204 No Content`: Action deleted successfully. (Returns `DeleteResourceDTO`)
    * `404 Not Found`: Action not found. (Returns `DeleteResourceDTO`)

### `GET /api/v1/global/actions/name/{name}`
**Summary:** Get global action by name
* **Parameters:**
    * `name` (path, **required**): Name of the action to retrieve.
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: Action retrieved successfully. (Returns `ActionResponseDTO`)
    * `404 Not Found`: Action not found. (Returns `ActionResponseDTO`)

---

## 🛂 roles-controller

### `GET /v1/roles`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO[]`)

### `POST /v1/roles`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RoleRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `GET /v1/roles/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `PUT /v1/roles/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RoleRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `GET /v1/roles/exists/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `boolean`)

### `DELETE /v1/roles/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`

---

## 🌐 global-roles-controller

### `GET /api/v1/global-roles`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO[]`)

### `POST /api/v1/global-roles`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RoleRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `GET /api/v1/global-roles/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `PUT /api/v1/global-roles/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RoleRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `DELETE /api/v1/global-roles/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`

### `POST /api/v1/global-roles/{id}/clone`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `newRoleName` (query, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

### `GET /api/v1/global-roles/templates`
* **Parameters:**
    * `isTemplate` (query, optional, default: `true`)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO[]`)

### `GET /api/v1/global-roles/name/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RoleResponseDTO`)

---

## 📦 global-resources-controller

### `GET /api/v1/global-resources`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO[]`)

### `POST /api/v1/global-resources`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `ResourceRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO`)

### `GET /api/v1/global-resources/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO`)

### `PUT /api/v1/global-resources/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `ResourceRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO`)

### `DELETE /api/v1/global-resources/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`

### `GET /api/v1/global-resources/type/{resourceType}`
* **Parameters:**
    * `resourceType` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO[]`)

### `GET /api/v1/global-resources/name/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `ResourceResponseDTO`)

---

## 🔗 role-permission-controller

### `GET /v1/role-permissions`
**Summary:** Get all role-permission mappings
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

### `POST /v1/role-permissions`
**Summary:** Create a role-permission mapping
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RolePermissionRequestDTO`
* **Responses:**
    * `201 Created`: Role-permission mapping created successfully. (Returns `RolePermissionResponseDTO`)

### `POST /v1/role-permissions/bulk`
**Summary:** Create multiple role-permission mappings in bulk
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RolePermissionBulkRequestDTO`
* **Responses:**
    * `201 Created`: Role-permission mappings created successfully. (Returns `RolePermissionResponseDTO[]`)

### `GET /v1/role-permissions/{id}`
**Summary:** Get role-permission mapping by ID
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: Role-permission mapping found. (Returns `RolePermissionResponseDTO`)

### `DELETE /v1/role-permissions/{id}`
**Summary:** Delete a role-permission mapping
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `204 No Content`: Role-permission mapping deleted successfully

### `GET /v1/role-permissions/role/{roleId}`
**Summary:** Get all permissions for a specific role
* **Parameters:**
    * `roleId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

### `GET /v1/role-permissions/permission/{permissionId}`
**Summary:** Get all roles that have a specific permission
* **Parameters:**
    * `permissionId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

### `GET /v1/role-permissions/has-all-permissions`
**Summary:** Check if a role has all specified permissions
* **Parameters:**
    * `roleId` (query, **required**): uuid
    * `permissionIds` (query, **required**): array of uuids
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `boolean`)

### `GET /v1/role-permissions/exists`
**Summary:** Check if a role-permission mapping exists
* **Parameters:**
    * `roleId` (query, **required**): uuid
    * `permissionId` (query, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `boolean`)

---

## 🛡️ global-permissions-controller

### `GET /api/v1/permissions`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

### `POST /api/v1/permissions`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `PermissionRequestDTO`
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO`)

### `GET /api/v1/permissions/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO`)

### `DELETE /api/v1/permissions/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`

### `GET /api/v1/permissions/resource/{resourceId}`
* **Parameters:**
    * `resourceId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

### `GET /api/v1/permissions/name/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO`)

### `GET /api/v1/permissions/action/{actionId}`
* **Parameters:**
    * `actionId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

---

## 🔗 global-role-permissions-controller

### `GET /api/role-permissions`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

### `POST /api/role-permissions`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Request Body:** (`application/json`)
    * `RolePermissionRequestDTO`
* **Responses:**
    * `201 Created`: (Returns `RolePermissionResponseDTO`)

### `GET /api/role-permissions/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO`)

### `DELETE /api/role-permissions/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `204 No Content`

### `GET /api/role-permissions/role/{roleId}`
* **Parameters:**
    * `roleId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

### `GET /api/role-permissions/permission/{permissionId}`
* **Parameters:**
    * `permissionId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `RolePermissionResponseDTO[]`)

---

## 🛡️ permission-controller

### `GET /v1/permissions`
* **Parameters:**
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

### `GET /v1/permissions/{id}`
* **Parameters:**
    * `id` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO`)

### `GET /v1/permissions/resource/{resourceId}`
* **Parameters:**
    * `resourceId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

### `GET /v1/permissions/name/{name}`
* **Parameters:**
    * `name` (path, **required**)
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO`)

### `GET /v1/permissions/action/{actionId}`
* **Parameters:**
    * `actionId` (path, **required**): uuid
    * `X-Tenant-Id` (header, **required**)
* **Responses:**
    * `200 OK`: (Returns `PermissionResponseDTO[]`)

---

# 📦 Schemas (Models)
---
### `ActionRequestDTO`
| Property | Type |
|---|---|
| `actionName` | string |
| `descriptions` | string |

---
### `ActionResponseDTO`
| Property | Type | Format |
|---|---|---|
| `actionId` | string | uuid |
| `actionName` | string | |
| `descriptions` | string | |
| `createdAt` | string | date-time |
| `updatedAt` | string | date-time |

---
### `DeleteResourceDTO`
| Property | Type |
|---|---|
| `status` | string |
| `message` | string |

---
### `PermissionRequestDTO`
| Property | Type | Format |
|---|---|---|
| `actionId` | string | uuid |
| `resourceId` | string | uuid |

---
### `PermissionResponseDTO`
| Property | Type |
|---|---|
| `permissionId` | string (uuid) |
| `name` | string |
| `description` | string |
| `actions` | `ActionResponseDTO` |
| `resources` | `ResourceResponseDTO` |

---
### `ResourceRequestDTO`
| Property | Type |
|---|---|
| `resourceName` | string |
| `description` | string |
| `resourceType` | string |

---
### `ResourceResponseDTO`
| Property | Type | Format |
|---|---|---|
| `resourceId` | string | uuid |
| `resourceName` | string | |
| `descriptions` | string | |
| `resourceType` | string | |

---
### `RolePermissionBulkRequestDTO`
| Property | Type | Description |
|---|---|---|
| `roleId` | string (uuid) | |
| `permissionIds` | string[] (uuid) | Array of permission UUIDs |

---
### `RolePermissionRequestDTO`
| Property | Type | Format |
|---|---|---|
| `roleId` | string | uuid |
| `permissionId` | string | uuid |

---
### `RolePermissionResponseDTO`
| Property | Type |
|---|---|
| `rolePermissionId` | string (uuid) |
| `roles` | `RoleResponseDTO` |
| `permissions` | `PermissionResponseDTO` |

---
### `RoleRequestDTO`
| Property | Type |
|---|---|
| `roleName` | string |
| `descriptions` | string |
| `isSystemRole` | boolean |
| `isTemplate` | boolean |

---
### `RoleResponseDTO`
| Property | Type | Format |
|---|---|---|
| `roleId` | string | uuid |
| `roleName` | string | |
| `descriptions` | string | |
| `isTemplate` | boolean | |
