//var cssText = "";
var css = document.createElement("style");
css.type = "text/css";
if ("textContent" in css) {
    css.textContent = cssText;
} else {
    css.innerText = cssText;
}
document.head.insertBefore(css, document.head.childNodes[0]);
