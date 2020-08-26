var reqSubmitted = false;
var origLink;
function checkSub(A) {
	if(!reqSubmitted) { 
		reqSubmitted = true;
		origLink = A.onclick;
		A.onclick = function onclick(){};		
		return true;
	} else {	
		A.onclick = function onclick(){};
		return false;	
	}	
	return false;
}

function unCheckSub(A) {
	reqSubmitted = false;
	A.onclick = origLink;
}