/** *************设置参数***************** */
function decimals(number) {
	if (number % 1 != 0) {
		return number;
	} else {
		return number + ".0";
	}
}
/** **********关闭详细层************ */
function hide(o) {
	var o = document.getElementById(o);
	o.style.display = "none";
}
