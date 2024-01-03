
function bigger(){
document.getElementById("text").style.fontSize= "24pt";
}

function radioFancy(){
document.getElementById("text").style.fontWeight= "bold";
document.getElementById("text").style.color= "blue";
document.getElementById("text").style.textDecoration= "underline";
}

function radioBoring(){
document.getElementById("text").style.font= "initial";
document.getElementById("text").style.color= "initial";
document.getElementById("text").style.textDecoration= "initial";
}

function moo(){
  document.getElementById("text").style.textTransform = "uppercase";
  let str = document.getElementById("text").value .split(".");;
  let str2=str.join("-Moo");
  document.getElementById("text").value = str2;
   
}