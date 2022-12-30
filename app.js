const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const mysql = require("mysql2");

const app = express();

app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static("public"));

const con = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    port: 3306,
    password: "123456",
    database: "blood-db"
});

con.connect((err) => {
    if (err) {
        throw err;
    }
    else {
        console.log("Connected to database");
    }
});

app.get("/", (req, res) => {
    res.render("home");
});

app.get("/register", (req, res) => {
    res.render("register");
});

app.get("/donors", (req, res) => {
    let sql = 'SELECT * FROM donors;';
    con.query(sql, function (error, results) {
        if (error) throw error;
        res.render("donors", { results: results });
    });
});

app.get("/recipients", (req, res) => {
    let sql = 'SELECT * FROM recipients;';
    con.query(sql, function (error, results) {
        if (error) throw error;
        res.render("recipients", { results: results });
    });
});

app.get("/hospital", (req, res) => {
    let sql = 'SELECT * FROM hospital;';
    con.query(sql, function (error, results) {
        if (error) throw error;
        res.render("hospital", { results: results });
    });
});

app.get("/register/register_p", (req, res) => {
    res.render("register_p");
});

app.get("/register/register_h", (req, res) => {
    res.render("register_h");
});

app.get("/transfer", (req, res) => {
    res.render("transfer");
});

app.get("/transfer/transfer_p", (req, res) => {
    res.render("transfer_p");
});

app.get("/transfer/transfer_h", (req, res) => {
    res.render("transfer_h");
});

app.get("/bank", (req, res) => {
    let sql = 'SELECT * FROM bank;';
    con.query(sql, function (error, results) {
        if (error) throw error;
        res.render("bank", { results: results });
    });
});

app.post("/register/register_p", (req, res) => {
    const name = req.body.name;
    const age = req.body.age;
    const dob = req.body.dob;
    const gender = req.body.gender;
    const b_grp = req.body.b_grp;
    const city = req.body.city;
    const phone = req.body.phone;
    const qty = req.body.qty;
    const role = req.body.role;

    let sql = "insert into person (name,age,dob,gender,bldgrp,city,phone,qty,role) values('" + name + "'," + age + ",'" + dob + "','" + gender + "','" + b_grp + "','" + city + "','" + phone + "'," + qty + ",'" + role + "');";
    con.query(sql, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log("Data inserted..");
            console.log(sql);
            res.render("register_p");
        }
    });
});

app.post("/register/register_h", (req, res) => {
    const name = req.body.name;
    const b_grp = req.body.b_grp;
    const needqty = req.body.needqty;
    const city = req.body.city;
    const phone = req.body.phone;

    let sql = "insert into hospital (name,bldgrp,needqty,city,phone) values('" + name + "','" + b_grp + "'," + needqty + ",'" + city + "','" + phone + "');";
    con.query(sql, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log("Data inserted..");
            console.log(sql);
            res.render("register_h");
        }
    });
});

app.post("/transfer", (req, res) => {
    const donorID = req.body.donorID;
    const recipientID = req.body.recipientID;

    let sql = "delete from person where pid in (" + donorID + "," + recipientID + ");";
    con.query(sql, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log("Data deleted..");
            console.log(result);
            console.log(sql);
            res.render("transfer");
        }
    });
});

app.listen(3000, (err) => {
    if (err) {
        console.log(err);
    }
    else {
        console.log("Server started on port 3000");
    }
});