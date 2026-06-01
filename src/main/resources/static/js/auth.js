const Auth = {
  isLoggedIn() {
    return !!localStorage.getItem('jwt_token');
  },

  getToken() {
    return localStorage.getItem('jwt_token');
  },

  setToken(token) {
    localStorage.setItem('jwt_token', token);
  },

  logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('ecom_user');
    localStorage.removeItem('ecom_cart');
    window.location.href = '/login.html';
  },

  getUser() {
    const raw = localStorage.getItem('ecom_user');
    return raw ? JSON.parse(raw) : null;
  },

  setUser(user) {
    localStorage.setItem('ecom_user', JSON.stringify(user));
  },

  getRole() {
    const token = this.getToken();
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role;
    } catch (e) { return null; }
  },

  getUserId() {
    const token = this.getToken();
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.userId;
    } catch (e) { return null; }
  },

  requireAuth() {
    if (!this.isLoggedIn()) {
      const redirect = encodeURIComponent(window.location.pathname + window.location.search);
      window.location.href = '/login.html?redirect=' + redirect;
      return false;
    }
    return true;
  },

  requireRole(roles) {
    if (!this.requireAuth()) return false;
    const role = this.getRole();
    if (!roles.includes(role)) {
      UI.toast('Access denied: insufficient privileges', 'error');
      setTimeout(() => window.location.href = '/index.html', 1500);
      return false;
    }
    return true;
  },

  async initUser() {
    if (this.isLoggedIn() && !this.getUser()) {
      try {
        const user = await API.getCurrentUser();
        this.setUser(user);
      } catch (e) {
        this.logout();
      }
    }
  },

  updateNav() {
    const nav = document.getElementById('main-nav');
    if (!nav) return;
    const user = this.getUser();
    const role = this.getRole();
    const isLoggedIn = this.isLoggedIn();

    let html = `
      <a href="/index.html" class="nav-link"><i class="fas fa-home"></i><span>Home</span></a>
      <a href="/products.html" class="nav-link"><i class="fas fa-box-open"></i><span>Products</span></a>
    `;

    if (isLoggedIn) {
      if (role === 'CUSTOMER') {
        html += `<a href="/customer-dashboard.html" class="nav-link"><i class="fas fa-user"></i><span>Account</span></a>`;
      } else if (role === 'EMPLOYEE') {
        html += `<a href="/employee-dashboard.html" class="nav-link"><i class="fas fa-briefcase"></i><span>Dashboard</span></a>`;
      } else if (role === 'ADMIN' || role === 'SUPER_ADMIN') {
        html += `<a href="/admin-dashboard.html" class="nav-link"><i class="fas fa-shield-alt"></i><span>Admin</span></a>`;
      }
      html += `
        <a href="/cart.html" class="nav-link cart-link">
          <i class="fas fa-shopping-cart"></i><span>Cart</span>
          <span class="cart-badge" id="cart-count">0</span>
        </a>
        <div class="user-menu">
          <span class="nav-link" style="cursor:default"><i class="fas fa-user-circle"></i><span>Hello, ${user ? user.userName : 'User'}</span></span>
          <button onclick="Auth.logout()" class="btn btn-sm btn-outline"><i class="fas fa-sign-out-alt"></i> Logout</button>
        </div>
      `;
    } else {
      html += `
        <a href="/login.html" class="nav-link"><i class="fas fa-sign-in-alt"></i><span>Login</span></a>
        <a href="/register.html" class="nav-link"><i class="fas fa-user-plus"></i><span>Register</span></a>
        <a href="/cart.html" class="nav-link cart-link">
          <i class="fas fa-shopping-cart"></i><span>Cart</span>
          <span class="cart-badge" id="cart-count">0</span>
        </a>
      `;
    }
    nav.innerHTML = html;
    Cart.updateBadge();
  }
};