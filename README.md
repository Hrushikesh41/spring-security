<h1>🔒 Spring Security Concepts</h1>

<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white" alt="Spring Security Badge">

<p><b>Dependencies</b> 📦: Use <code>spring-boot-starter-security</code> and <code>jjwt</code> for security.</p>
<p><b>Authentication</b> 🔐: Verifies user identity; authorization restricts access.</p>
<p><b>JWT</b> 🌐: Stateless auth with <code>Bearer</code> tokens.</p>
<p><b>Security Config</b> ⚙️: Defines rules using <code>SecurityFilterChain</code>.</p>
<p><b>Custom Filter</b> 🧩: Validates JWT via <code>OncePerRequestFilter</code>.</p>
<p><b>User Details</b> 👤: Loads users with <code>UserDetailsService</code>.</p>
<p><b>Passwords</b> 🔑: Hashes credentials with <code>BCryptPasswordEncoder</code>.</p>
<p><b>Roles</b> 🚪: Secures routes using <code>.hasRole()</code>.</p>
<p><b>JWT Handling</b> 🔗: Signs/verifies tokens with <code>jjwt</code>.</p>
<p><b>Blacklist</b> 🛑: Invalidates JWTs on logout.</p>
<p><b>Controller</b> 🎮: Manages <code>/login</code>, <code>/register</code> endpoints.</p>
<p><b>Registration</b> ✨: Saves users with secure passwords.</p>
<p><b>CSRF</b> 🚫: Disabled for stateless APIs.</p>
<p><b>Sessions</b> 🌫️: Uses <code>STATELESS</code> policy.</p>
<p><b>JSON</b> 📱: Sends <code>application/json</code> via <code>fetch</code>.</p>
<p><b>Redirects</b> 🛤️: Routes users by role.</p>
<p><b>Logout</b> 👋: Blacklists JWT, clears storage.</p>
<p><b>Admin</b> 🛡️: Restricts admin role creation.</p>
<p><b>Database</b> 💾: Stores users with JPA/H2.</p>
<p><b>Errors</b> ❌: Returns HTTP status for issues.</p>
<p><b>Logging</b> 📜: Tracks filter and role events.</p>

<h2>🤝 Contribute</h2>
<p>Add concepts via PRs! 🚀</p>

<h2>📜 License</h2>
<p><a href="LICENSE">MIT</a></p>
