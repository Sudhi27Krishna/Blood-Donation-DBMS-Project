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

app.get("/home1", (req, res) => {
    res.render("home1");
});

app.get("/login", (req, res) => {
    res.render("login", { log: true });
});

app.get("/about", (req, res) => {
    res.render("about");
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
    let sql2 = 'SELECT * FROM history_p;';
    con.query(sql2, function (error, results) {
        if (error) throw error;
        res.render("transfer_p", { results: results });
    });
});

app.get("/transfer/transfer_h", (req, res) => {
    let sql2 = 'SELECT * FROM history_h;';
    con.query(sql2, function (error, results) {
        if (error) throw error;
        res.render("transfer_h", { results: results });
    });
});

app.get("/bank", (req, res) => {
    let sql = 'SELECT * FROM bank;';
    con.query(sql, function (error, results) {
        if (error) throw error;
        res.render("bank", { results: results });
    });
});

app.post("/login", (req, res) => {
    const name = req.body.username;
    const pass = req.body.password;

    let query = "SELECT * FROM login WHERE username = ? AND pass = ?";
    con.query(query, [name, pass], (err, results) => {
        if (err) throw err;

        if (results.length > 0) {
            res.render("home1");
        } else {
            res.render("login", { log: false });
        }
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
    const qty = req.body.qty;
    const city = req.body.city;
    const phone = req.body.phone;

    let sql = "insert into hospital (name,bldgrp,qty,city,phone) values('" + name + "','" + b_grp + "'," + qty + ",'" + city + "','" + phone + "');";
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

app.post("/transfer/transfer_p", (req, res) => {
    const donorID = req.body.donorID;
    const recipientID = req.body.recipientID;
    const t_date = req.body.t_date;
    const t_grp = req.body.b_grp;
    var d_name;
    var r_name;
    var t_qty;

    let query1 = "select name from person where pid=" + donorID + ";";
    con.query(query1, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            d_name = result[0].name;
            console.log(t_date);
            console.log(t_grp);
            console.log(d_name);
        }
    });

    let query2 = "select name,qty from person where pid=" + recipientID + ";";
    con.query(query2, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            r_name = result[0].name;
            t_qty = result[0].qty;
            console.log(r_name);
            console.log(t_qty);
        }

        let query3 = "insert into history_p (bldgrp, d_name, r_name, qty, t_date) values(?, ?, ?, ?, ?)";
        con.query(query3, [t_grp, d_name, r_name, t_qty, t_date], (err, result) => {
            if (err) {
                console.log(err);
            }
            else {
                console.log(result);
            }
        });
    });

    let sql = "delete from person where pid in (" + donorID + "," + recipientID + ");";
    con.query(sql, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log("Data deleted..");
            console.log(result);
            console.log(sql);
            res.redirect("/transfer/transfer_p");
        }
    });
});

app.post("/transfer/transfer_h", (req, res) => {
    const hID = req.body.hID;
    const t_date = req.body.t_date;
    const t_grp = req.body.b_grp;
    const d_id = req.body.d_id;
    let h_name;
    let d_name;
    let qty;

    let query = "select name from person where pid = " + d_id + ";";
    con.query(query, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            d_name = result[0].name;
            console.log(result);
            console.log(query);
        }
    });

    let query1 = "select name, qty from hospital where hid = " + hID + ";";
    con.query(query1, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            qty = result[0].qty;
            h_name = result[0].name;
            console.log(qty);
            console.log(h_name);
        }

        let query2 = "insert into history_h (bldgrp, name, d_name, qty, t_date) values(?, ?, ?, ?, ?)";
        con.query(query2, [t_grp, h_name, d_name, qty, t_date], (err, result) => {
            if (err) {
                console.log(err);
            }
            else {
                console.log(result);
                console.log(query2);
            }
        });
    });

    let query3 = "delete from person where pid = " + d_id + ";";
    con.query(query3, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log(result);
            console.log(query3);
        }
    });

    let sql = "delete from hospital where hid = " + hID + ";";
    con.query(sql, (err, result) => {
        if (err) {
            console.log(err);
        }
        else {
            console.log("Data deleted..");
            console.log(result);
            console.log(sql);
            res.redirect("/transfer/transfer_h");
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