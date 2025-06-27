//정규식


//이메일 정규식
export const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
//핸드폰 번호 정규식
export const phoneNumberRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
//비밀번호 정규식
export const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&~])[A-Za-z\d@$!%*#?&~]{8,}$/;
//공백,특수문자 제외 2~10글자
export const exceptBlankAndSpecialChar = /^[^\s!@#$%^&*(),.?":{}|<>]{2,10}$/;

//사업자명 등록 용 정규식 추가 공백 가능, 특수문자 괄호 가능 , 숫자 가능
export const businessNamePattern = /^[\w\s\uAC00-\uD7A3().\[\]{}&\/·,-]+$/;
//공백,특수문자,숫자 제외 2~10글자
export const exceptBlankSpecialNumWithLength = /^[^\s!@#$%^&*(),.?":{}|<>0-9]{2,10}$/;
//6~16자리 숫자만 허용
export const accountRegex = /^\d{6,16}$/;
//10자리 숫자만 허용
export const busiResiNbRegex = /^\d{10}$/;
//8자리 숫자만 허용
export const dateRegex = /^\d{8}$/;