var page = 1;
const key = ""
var totalStr = "";

//ajax data 불러오기
function actorList() {
    $.ajax({
        type: "GET",
        url: "https://api.themoviedb.org/3/person/popular?" +
            `api_key=${key}` +
            "&language=ko&" +
            `page=${page}`,
        data: {},
        dataType: "json",
        success: function (data) {

            let list = [];
            data.results.forEach((item) => {
                list.push([
                    str = `<form action=/actor/${item.id} method="GET">`,
                    str += '<div>',
                    str += '<div class="actorImg">' + `<input type="image" name="profile" src=https://image.tmdb.org/t/p/w500${item.profile_path} onerror="this.src='/assets/null/null.png';">` + '</div>',
                    str += '<div class="actorName">' + item.name + '</div>',
                    str += `<input type="checkbox" class="dataId" name="actorId" value=${item.id} checked>`,
                    str += '</div>',
                    str += '</form>'
                ]);
                totalStr += str;
                $('.actorList').append(str)
            });
            sessionStorage.setItem("actorTotalStr", totalStr);
            sessionStorage.setItem("actorPage", page);
        }
    });
}

// 최초 목록 갱신
$(document).ready(function (event) {

    if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
        totalStr = sessionStorage.getItem("actorTotalStr");
        $('.actorList').append(totalStr);
        if (sessionStorage.getItem("actorPage") != 1) {
            $('.moreButton').hide();
        }
    } else {
        actorList();
    }
});

// 글릭시 다음 페이지 갱신
$(document).ready(function () {
    $('.moreButton').click(function () {
        $('.moreButton').hide();
        page++
        actorList();
    });
});

//스크롤 발생 이벤트 처리
$(document).ready(function () {
    $(window).scroll(function () {
        var scrT = $(window).scrollTop();
        if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            page = sessionStorage.getItem("actorPage");

            setTimeout(function () {
                if(scrT == $(document).height() - $(window).height()) {
                    page++
                    movieList();
                }
            }, 50)
        }
        setTimeout(function () {
            if (scrT == $(document).height() - $(window).height() && page != 1) {
                page++
                actorList();
            }
        }, 50)
    });
});

// 스크롤 맨위로 올리기
$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop() > 200) {
            $('.top').fadeIn();
        } else {
            $('.top').fadeOut();
        }
    });
    $('.top').click(function () {
        $('html, body').animate({scrollTop: 0}, 400);
        return false;
    });
});







