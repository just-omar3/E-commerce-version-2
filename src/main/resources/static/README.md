# ShopMaster E-Commerce Frontend

## Spring Boot Integration Guide

### Step 1: Place Files Correctly

Copy ALL files from this zip into your Spring Boot project:

```
src/main/resources/static/
    index.html
    login.html
    register.html
    products.html
    product-detail.html
    cart.html
    checkout.html
    order-success.html
    customer-dashboard.html
    employee-dashboard.html
    admin-dashboard.html
    404.html
    css/
        style.css
    js/
        api.js
        auth.js
        cart.js
        ui.js
        main.js
```

### Step 2: Update SecurityConfig.java

Replace your current `SecurityConfig.java` with the provided `SecurityConfig_Fixed.java` (or merge the changes).

The KEY change is that ALL static file paths are now explicitly permitted:

```java
.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/assets/**").permitAll()
```

### Step 3: Why Products Were Not Reachable

1. **Absolute paths**: The original frontend used `/css/style.css` which works on a dedicated web server but NOT inside Spring Boot's static resource handler. Spring Boot maps `static/` folder contents to the ROOT `/`, so the correct path is `css/style.css` (relative).

2. **Security blocking**: If Spring Security blocks `/css/**` or `/js/**`, the browser cannot load styles and scripts, making the page appear broken even though the HTML loads.

3. **API_BASE empty string**: The `api.js` uses `const API_BASE = '';` which means all API calls go to the same origin (e.g., `http://localhost:8080/products`). This is correct for same-origin deployment.

### Step 4: Access URLs

After starting Spring Boot on port 8080:

- Homepage: http://localhost:8080/
- Login: http://localhost:8080/login.html
- Products: http://localhost:8080/products.html
- Customer Dashboard: http://localhost:8080/customer-dashboard.html
- Employee Dashboard: http://localhost:8080/employee-dashboard.html
- Admin Dashboard: http://localhost:8080/admin-dashboard.html

### Step 5: CORS (if running frontend separately)

If you serve the frontend from a different port (e.g., VS Code Live Server on port 5500), your backend CORS config (`WebConfig.java`) already allows all origins:

```java
.allowedOrigins("*")
```

But you MUST change `api.js`:
```javascript
const API_BASE = 'http://localhost:8080'; 
```

### Troubleshooting Checklist

| Symptom | Cause | Fix |
|---------|-------|-----|
| Page loads but no styling | CSS blocked by Security or wrong path | Check SecurityConfig permits `/css/**` |
| Buttons don't work | JS blocked by Security or wrong path | Check SecurityConfig permits `/js/**` |
| API calls return 403 | JWT missing or expired | Login again, check token in localStorage |
| API calls return 404 | Wrong API_BASE or backend not running | Ensure backend is on same origin or set API_BASE |
| "No static resource" error | Spring Boot 3.2+ behavior | Ensure static files are in `src/main/resources/static/` |

### File Path Reference (Spring Boot Default)

| File Location | URL Path |
|--------------|----------|
| `src/main/resources/static/index.html` | `http://localhost:8080/index.html` |
| `src/main/resources/static/css/style.css` | `http://localhost:8080/css/style.css` |
| `src/main/resources/static/js/api.js` | `http://localhost:8080/js/api.js` |

### Important: Do NOT use `/static/` in URLs

WRONG: `<link href="/static/css/style.css">`  
CORRECT: `<link href="css/style.css">`

Spring Boot automatically strips the `/static/` prefix when serving files.
