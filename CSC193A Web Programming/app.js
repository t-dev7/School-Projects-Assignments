'use strict';
const express = require('express');
const app = express();


  // define endpoints for exercise 1 here
  app.get("/math/circle/:r",  (req, res) => { 
    let r = Number(req.params.r);
    circumference = 2 * Math.PI * r;
    area = Math.PI * r * r;
    res.send("{area: "+ area + ", circumference:" + circumference + "}" );
  });

  // define endpoints for exercise 2 here
  app.get("/hello/name", (req, res) => { 
    let firstName = req.query.first;
    let lastName = req.query.last;
    if (!(lastName && firstName)) {
        res.status(400).send("Missing Required GET parameters: first, last");
      } 
    else if(!(lastName)){
        res.status(400).send("Missing Required GET parameters: last");
    } 
    else if(!(firstName)){
        res.status(400).send("Missing Required GET parameters: first");
    } 
      else {
    res.send("Hello" + firstName + " " + lastName);
      }
  });



const PORT = process.env.PORT || 8000;
app.listen(PORT);
