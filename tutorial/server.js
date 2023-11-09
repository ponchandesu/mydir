const http = require("http");
const PORT = 8000;
const html = require("fs").readFileSync("./index.html");

//webserver 作成
const server = http.createServer((rew,res) =>{
    //ブラウザからアクセスが来た時の処理
    res.writeHead(200,{"content-Type":"text/html"});
    res.write(html);
    res.end();

    if(req.method == "GET"){

    }

    if(req.method == "POST"){
        
    }
});

server.listen(PORT, () => {
    console.log("server running!");
})