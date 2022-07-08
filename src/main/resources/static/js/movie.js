var page = 1;
const key = "";
var totalStr = "";


list();
$(document).ready(function (){
   $("select[name=sortLte]").change(function (){
       $(".movieList").empty();
        if($("select[name=sortLte]").val() == "10"){

            listLte();
        }else if($("select[name=sortLte]").val() != "10") {

            list();
        }
   })
});


//ajax data 불러오기
function list() {
    function movieList() {
        $.ajax({
            type: "GET",
            url: "https://api.themoviedb.org/3/discover/movie" +
                `?api_key=${key}` +
                "&language=ko&region=krr%2Cus" +
                "&sort_by=popularity.desc" +
                "&include_adult=false" +
                "&include_video=false" +
                `&page=${page}` +
                "&vote_average.gte=1" +
                "&vote_average.lte=6" +
                "&with_watch_monetization_types=flatrate",
            data: {},
            dataType: "json",
            success: function (data) {

                let list = [];
                data.results.forEach((item) => {
                    list.push([
                        str = `<form action=/movie/${item.id} method="GET">`,
                        str += '<div class="listGroup">',
                        str += `<input type="image" name="poster" class="moviePoster" src=https://image.tmdb.org/t/p/w500${item.poster_path}>`,
                        str += '<div class="movieScore">' + `TMDb ${item.vote_average} 망작선` + '</div>',
                        str += '<div class="movieTitle">' + item.title + '</div>',
                        str += `<input type="checkbox" class="dataId" name="movieId" value=${item.id} checked>`,
                        str += '</div>',
                        str += '</form>'
                    ]);
                    totalStr += str;
                    $('.movieList').append(str)
                });
                sessionStorage.setItem("movieTotalStr", totalStr);
                sessionStorage.setItem("moviePage", page)
            }
        });
    }

// 최초 목록 갱신
    $(document).ready(function (event) {

        if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            totalStr = sessionStorage.getItem("movieTotalStr");
            $('.movieList').append(totalStr);
            if (sessionStorage.getItem("moviePage") != 1) {
                $('.moreButton').hide();
            }
        } else {
            movieList();
        }
    })

// 글릭시 다음 페이지 갱신
    $(document).ready(function () {
        $('.moreButton').click(function () {
            $('.moreButton').hide();
            page++
            movieList();
        });
    });

//스크롤 발생 이벤트 처리
    $(document).ready(function (event) {
        $(window).scroll(function () {
            var scrT = $(window).scrollTop();
            if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
                page = sessionStorage.getItem("moviePage");

                setTimeout(function () {
                    if (scrT == $(document).height() - $(window).height()) {
                        page++
                        movieList();
                    }
                }, 50)
            }
            setTimeout(function () {
                if (scrT == $(document).height() - $(window).height() && page != 1) {
                    page++
                    movieList();
                }
            }, 50)
        });
    });

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
}
function listLte() {
    function movieList() {
        $.ajax({
            type: "GET",
            url: "https://api.themoviedb.org/3/discover/movie" +
                `?api_key=${key}` +
                "&language=ko&region=krr%2Cus" +
                "&sort_by=popularity.desc" +
                "&include_adult=false" +
                "&include_video=false" +
                `&page=${page}` +
                "&vote_average.gte=1" +
                "&vote_average.lte=10" +
                "&with_watch_monetization_types=flatrate",
            data: {},
            dataType: "json",
            success: function (data) {

                let list = [];
                data.results.forEach((item) => {
                    list.push([
                        str = `<form action=/movie/${item.id} method="GET">`,
                        str += '<div class="listGroup">',
                        str += `<input type="image" name="poster" class="moviePoster" src=https://image.tmdb.org/t/p/w500${item.poster_path}>`,
                        str += '<div class="movieScore">' + `TMDb ${item.vote_average} 망작선` + '</div>',
                        str += '<div class="movieTitle">' + item.title + '</div>',
                        str += `<input type="checkbox" class="dataId" name="movieId" value=${item.id} checked>`,
                        str += '</div>',
                        str += '</form>'
                    ]);
                    totalStr += str;
                    $('.movieList').append(str)
                });
                sessionStorage.setItem("movieTotalStr", totalStr);
                sessionStorage.setItem("moviePage", page)
            }
        });
    }

// 최초 목록 갱신
    $(document).ready(function (event) {

        if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            totalStr = sessionStorage.getItem("movieTotalStr");
            $('.movieList').append(totalStr);
            if (sessionStorage.getItem("moviePage") != 1) {
                $('.moreButton').hide();
            }
        } else {
            movieList();
        }
    })

// 글릭시 다음 페이지 갱신
    $(document).ready(function () {
        $('.moreButton').click(function () {
            $('.moreButton').hide();
            page++
            movieList();
        });
    });

//스크롤 발생 이벤트 처리
    $(document).ready(function (event) {
        $(window).scroll(function () {
            var scrT = $(window).scrollTop();
            if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
                page = sessionStorage.getItem("moviePage");

                setTimeout(function () {
                    if (scrT == $(document).height() - $(window).height()) {
                        page++
                        movieList();
                    }
                }, 50)
            }
            setTimeout(function () {
                if (scrT == $(document).height() - $(window).height() && page != 1) {
                    page++
                    movieList();
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
}

