# Java Raw HTTP Server (Socket Programming)

This project implements a basic HTTP server from scratch using Java sockets.  
It supports basic **CRUD operations** via HTTP methods: `GET`, `POST`, `PUT`, and `DELETE`.

## 📁 Features

- Serve static files from a local directory
- Save files using `POST` and `PUT`
- Retrieve file contents using `GET`
- Delete files using `DELETE`
- Echo endpoint and User-Agent detection
- Multi-threaded handling of concurrent clients

📝 File CRUD via HTTP
📤 POST /files/{filename}
curl -i -X POST http://localhost:4221/files/newfile.txt \
     --data 'hello' \
     -H "Content-Type: application/octet-stream"

🛠 PUT /files/{filename}
Creates or overwrites the file.
curl -i -X PUT http://localhost:4221/files/newfile.txt \
     --data 'updated content' \
     -H "Content-Type: application/octet-stream"
     
❌ DELETE /files/{filename}
Deletes a file if it exists.
curl -i -X DELETE http://localhost:4221/files/newfile.txt


🛡 Status Codes Returned
200 OK – Successful retrieval or update

201 Created – File created successfully

400 Bad Request – Missing/invalid headers or body

404 Not Found – File or path doesn't exist


✍️ Author
Prasann Kumar
B.Tech Engineering Physics, Delhi Technological University
