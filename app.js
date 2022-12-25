const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");

const app = express();

app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static("public"));

app.get("/", (req, res) => {
    res.render("home");
});

app.get("/register", (req, res) => {
    res.render("register");
});

app.get("/donors", (req, res) => {
    res.render("donors");
});

app.get("/recipients", (req, res) => {
    res.render("recipients");
});

app.get("/hospital", (req, res) => {
    res.render("hospital");
});

app.listen(3000, (err) => {
    if(err){
        console.log(err);
    }
    else{
        console.log("Server started on port 3000");
    }
});