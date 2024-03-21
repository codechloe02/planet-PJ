// 폼을 만들어 Post로 전송을 해주는 자바스크립트 함수
// url : 이동 주소
// params : 전달할 내용을 담은 JSON객체
function sendPost(url, params) { 
	var form = document.createElement('form'); // 폼태그 작성
	form.setAttribute('method', 'post'); // 메서드를 POST로
	form.setAttribute('action', url); // action속성 지정

	// 전달할 내용을 반복
	for (var key in params) {
		var hiddenField = document.createElement('input'); // input태그 작성
		hiddenField.setAttribute('type', 'hidden'); // 타입을 hidden으로
		hiddenField.setAttribute('name', key); // 키를 name 속성으로 
		hiddenField.setAttribute('value', params[key]); // 값을 value속성으로
		form.appendChild(hiddenField); // 폼태그에 추가
	}
	document.body.appendChild(form); // 폼을 body에 붙이고
	form.submit(); // 전송을 시킨다.
}


