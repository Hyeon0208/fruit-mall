$(document).on('click', '#findPwBtn', () => {
    axios({
        method: "get",
        url: `/api/v1/findPw/${encodeURIComponent($("#user_email").val())}`
    }).then(res => {
        if (res.data === "success") {
            $('.txt04').show(); // 이메일이 확인되었을 때

            $('.txt04 a').click(function() {
                $('.txt04').hide(); // 확인 버튼 클릭 시 모달 닫힘
                window.location.href = "/changePw?user_email=" + encodeURIComponent($("#user_email").val());
            });

        } else if (res.data === "not_found") {
            $('.txt05').show(); // 이메일(ID)가 존재하지 않을 때

            $('.txt05 a').click(function() {
                $('.txt05').hide(); // 닫기 버튼 클릭 시 모달 닫힘
            });
        }
    })
});
