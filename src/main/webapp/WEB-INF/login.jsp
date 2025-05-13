<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script>
        function getCookie(name) {
            const value = "; " + document.cookie;
            const parts = value.split("; " + name + "=");
            if (parts.length === 2) {
                return parts.pop().split(";").shift();
            }
            return null;
        }

        function restoreAutoLoginState() {
            const uuid = getCookie("rememberMe");
            const idInput = document.querySelector('input[name="mid"]');
            const checkbox = document.querySelector('input[name="auto"]');

            if (uuid) {
                const savedId = localStorage.getItem("rememberedId");
                if (savedId) {
                    idInput.value = savedId;
                    checkbox.checked = true;
                }
            }
        }

        function handleAutoLoginCheckbox() {
            const checkbox = document.querySelector('input[name="auto"]');
            const idInput = document.querySelector('input[name="mid"]');
            const pwInput = document.querySelector('input[name="mpw"]');

            if (!checkbox.checked) {
                idInput.value = '';
                pwInput.value = '';
                localStorage.removeItem('rememberedId');
            }
        }

        function saveIdOnSubmit() {
            const checkbox = document.querySelector('input[name="auto"]');
            const idInput = document.querySelector('input[name="mid"]');
            if (checkbox.checked) {
                localStorage.setItem("rememberedId", idInput.value);
            }
        }

        // 페이지 로드 시 자동 로그인 상태 복원
        window.addEventListener("DOMContentLoaded", restoreAutoLoginState);
    </script>
</head>
<body>
<c:if test="${param.result =='error'}">
    <h1>로그인 하세욧!</h1>
</c:if>

<form action="/login" method="post" onsubmit="saveIdOnSubmit()">
    <input type="text" name="mid" placeholder="ID">
    <input type="text" name="mpw" placeholder="PASSWORD">
    <input type="checkbox" name="auto" onchange="handleAutoLoginCheckbox()">자동 로그인
    <button type="submit">LOGIN</button>
</form>
</body>
</html>
