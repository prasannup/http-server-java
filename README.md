<!DOCTYPE html>
<html lang="en">


  <h1>🚀 Java Raw HTTP Server – Socket Powered!</h1>
  <p>Welcome to your very own handcrafted <strong>HTTP Server built in Java using sockets</strong> – no frameworks, just pure core Java! 🧵⚙️</p>

  <p>This server supports essential HTTP methods:</p>
  <ul>
    <li><code>GET</code> 🟢</li>
    <li><code>POST</code> 🟡</li>
    <li><code>PUT</code> 🟣</li>
    <li><code>DELETE</code> 🔴</li>
  </ul>

  <p>Use it to <strong>serve files</strong>, <strong>create/update</strong> them, or even <strong>delete</strong> files from a specified directory.</p>

  <h2>🏁 Getting Started</h2>

  <h3>🔨 Compile the server:</h3>
  <pre><code>javac -d out src/main/java/com/prasann/httpserver/*.java</code></pre>

  <h3>▶️ Run the server:</h3>
  <pre><code>java -cp out com.prasann.httpserver.Main --directory testfiles</code></pre>

  <p>By default, the server listens on <code>http://localhost:4221</code>.</p>

  <h2>🌐 Supported Endpoints</h2>

  <h3>✅ GET /</h3>
  <pre><code>curl -i http://localhost:4221/</code></pre>

  <h3>📢 GET /echo/{your-message}</h3>
  <pre><code>curl -i http://localhost:4221/echo/hello-world</code></pre>

  <h3>🧠 GET /user-agent</h3>
  <pre><code>curl -i http://localhost:4221/user-agent</code></pre>

  <h3>📂 GET /files/{filename}</h3>
  <pre><code>curl -i http://localhost:4221/files/notes.txt</code></pre>

  <h2>✍️ File Operations</h2>

  <h3>📤 POST /files/{filename}</h3>
  <pre><code>curl -i -X POST http://localhost:4221/files/hello.txt \
  --data 'hello' \
  -H "Content-Type: application/octet-stream"</code></pre>

  <h3>🔄 PUT /files/{filename}</h3>
  <pre><code>curl -i -X PUT http://localhost:4221/files/hello.txt \
  --data 'updated content' \
  -H "Content-Type: application/octet-stream"</code></pre>

  <h3>❌ DELETE /files/{filename}</h3>
  <pre><code>curl -i -X DELETE http://localhost:4221/files/hello.txt</code></pre>

  <h2>📡 Sample Status Codes</h2>
  <table class="status-table">
    <thead>
      <tr><th>Code</th><th>Meaning</th></tr>
    </thead>
    <tbody>
      <tr><td>200</td><td>✅ OK</td></tr>
      <tr><td>201</td><td>🆕 Created</td></tr>
      <tr><td>400</td><td>❌ Bad Request</td></tr>
      <tr><td>404</td><td>❓ Not Found</td></tr>
    </tbody>
  </table>

  <h2>🗂 Project Structure</h2>
  <pre><code>codecrafters-http-server-java/
├── src/
│   └── main/java/com/prasann/httpserver/
│       ├── Main.java
│       └── ClientHandler.java
├── testfiles/
│   └── hello.txt</code></pre>

  <h2>🙋‍♂️ About the Author</h2>
  <p><strong>Prasann Kumar</strong><br>
  B.Tech in Engineering Physics, DTU<br>
  </p>

  

  

</body>
</html>

