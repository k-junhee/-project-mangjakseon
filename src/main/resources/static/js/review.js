
    //리뷰 등록
    $(document).ready(function () {
        var movieId = $('.movieId').val();
        //console.log(movieId);
        //var listGroup = $(".reviewContent1");

        //나중에 '/reviews/review/'+movieId로 바꾸기
        $.getJSON('/movies/movie/' + movieId, function (arr) {
            //console.log(arr);
        })//end getJSON

        //리뷰 출력 될 영역
        var listGroup = $(".reviewContent1");

        //날짜 처리
        function formatTime(str) {
            var date = new Date(str);

            return date.getFullYear() + '/' +
                (date.getMonth() + 1) + '/' +
                date.getDate() + ' ' +
                date.getHours() + ':' +
                date.getMinutes();
        }

        //리뷰 출력 코드
        function loadJSONData() {
            $.getJSON('/movies/movie/' + movieId, function (arr) {
                //console.log(arr);

                var str = "";
                $.each(arr, function (idx, review) {
                    console.log(review);
                    str += '<div class="reviewBox">';
                    str += '<div class="writeInfo">';
                    str += '<div class="profileBox">';
                    str += `<img class="profileImage" src="/profile_images/${review.profileImage}" onerror=this.src="/assets/null/null.png">`;
                    str += '</div>';
                    str += '<div class="nickNameBox">'+'<span>'+'Review by&nbsp'+'</span>' +'<span>'+ review.nickName +'</span>'+ '&nbsp' +'<span>'+ review.score +"점" + '</span>' ;
                    str += '</div>';
                    str += '<div class="edit" id="remove'+idx+'" data-reviewNum="' + review.reviewNum + '" data-toggle="modal" data-target="#staticBackdrop">'+ '<span id="userChk'+idx+'">'+ "리뷰 수정"+'</span>';
                    str += '</div>';
                    str += '</div>';
                    str += '<div class="reviewContents">';
                    str += '<div class="reviewTitle">' +'<span>'+ review.reviewTitle +'</span>';
                    str += '</div>';
                    str += '<div class="reviewCont">' +'<span>'+ review.reviewContent +'</span>';
                    str += '</div>';
                    str += '</div>';
                    str += '<div class="likesAndComment">';
                    str += '<i class="fa-solid fa-heart" id="heart" onclick="heart('+review.reviewNum+')">' +'&nbsp'+ review.likeCount + '</i>'
                    str += '<div class="comment">' + '댓글 보기';
                    str += '</div>';
                    str += '</div>';
                    str += '</div>';
                    str += `<input type="hidden" value='${review.reviewNum}'>`;
                    str += '<input type="hidden" id="userId'+idx+'" value="'+review.memberId+'"/>';

                    str += '<script>';
                    str += '    $(document).ready(function(){';
                    str += '        var userId'+idx+' = document.getElementById("userId'+idx+'").value;';
                    str += '        var userInfo = document.getElementById("userInfo").value;';
                    str += '        var userChk'+idx+' = document.getElementById("userChk'+idx+'");';
                    str += '        var remove'+idx+' = document.getElementById("remove'+idx+'");';
                    str += '        console.log(remove'+idx+');';
                    str += '        if(userInfo != userId'+idx+'){';
                    str += '            userChk'+idx+'.style.display="none";';
                    str += '            remove'+idx+'.style.display="none";';
                    str += '        }else{;';
                    str += '            userChk'+idx+'.style.display="block";';
                    str += '        }';
                    str += '    });';
                    str += '</script>';
                })

                listGroup.html(str);
            });
        }

        //리뷰 출력
        loadJSONData();

        //리뷰 등록
        $("#reviewModalButton").click(function () {

            $('input[name="reviewTitle"]').val('');
            $('.reviewContent').val('');

            $(".reviewRemove, .reviewModify").hide();
            $(".reviewSave").show();
        });

        //리뷰 저장 버튼 누르면 ajax로 controller에 전송
        $(".reviewSave").click(function () {

            var review = {
                reviewTitle: $('input[name="reviewTitle"]').val(),
                reviewContent: $("textarea.reviewContent").val(),
                movieId: $('input[name="movieId"]').val(),
                score: $('input[name="score"]').val(),
                likeCount: 0,
                memberId: $('input[name="memberId"]').val(),
                nickName: $('input[name="nickName"]').val(),
                profileImage: $('input[name="profileImage"]').val()
            }
            console.log(review + "리뷰");

            $.ajax({
                url: '/movies',
                method: 'post',
                data: JSON.stringify(review),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {

                    var newReviewNum = parseInt(data);
                    // alert("댓글이 등록되었습니다.")
                    loadJSONData();
                }
            })
        });

        //리뷰 클릭 시 수정, 삭제 모달
        $('.reviewContent1').on("click", ".edit", function () {
            var reviewNum = $(this).data(reviewNum);
            //console.log(reviewNum);

            const obj = JSON.stringify(reviewNum);
            //console.log(obj);

            const obj2 = JSON.parse(obj);
            //console.log(obj2.reviewnum);

            $("input[name='reviewTitle']").val($(this).find('.card-body2-reviewTitle').html());
            $('.reviewContent').val($(this).find('.card-body4-reviewContent').html());
            $("input[name='reviewNum']").val(reviewNum.reviewnum);

            $(".reviewSave").hide();
            $(".reviewRemove,.reviewModify").show();
        });

        $(".reviewRemove").on("click", function () {
            var reviewNum = $("input[name='reviewNum']").val(); //모달창에 보이는 댓글 번호 hidden처리 되어있음
            //console.log(reviewNum);
            $.ajax({
                url: '/movies/' + reviewNum,
                method: 'delete',

                success: function (result) {
                    //console.log("result: " + result);
                    if (result === 'success') {
    //                    alert("댓글이 삭제되었습니다");
                        loadJSONData();
                    }
                }
            })
        });

        $(".reviewModify").click(function () {
            var reviewNum = $("input[name='reviewNum']").val();

            var review = {
                reviewNum: reviewNum,
                reviewTitle: $('input[name="reviewTitle"]').val(),
                reviewContent: $("textarea.reviewContent").val(),
                movieId: $('input[name="movieId"]').val(),
                score: $('input[name="score"]').val()
            }
            //console.log(review);

            $.ajax({
                url: '/movies/' + reviewNum,
                method: 'put',
                data: JSON.stringify(review),
                contentType: 'application/json; charset=utf-8',
                success: function (result) {

                    //console.log("RESULT: " + result);

                    if (result === 'success') {
    //                    alert("댓글이 수정되었습니다.");
                        loadJSONData();
                    }
                }
            });
        });
    });

    //좋아요
    function heart(reviewNum) {
            var memberId = $("#userInfo").val();

            var param = {"memberId": memberId, "reviewNum": reviewNum}
            console.log(param);

            $.ajax({
                type: "POST",
                data: JSON.stringify(param),
                url: "/heart",
                dataType: "JSON",
                contentType: 'application/json; charset=utf-8',
                traditional: true,
                success: function () {
                    alert("서비스 준비중 입니다.");

                },
                error: function () {
                    if(memberId == null || ""){
                        alert("서비스 준비중 입니다.");
                    }
                }
            });
        // $.ajax({
        //     type: "DELETE",
        //     data: JSON.stringify(param),
        //     url: "/heart",
        //     contentType: 'application/json',
        //     traditional: true,
        //     success: function () {
        //         alert("완료");
        //     },
        //     error: function () {
        //         alert("오류");
        //     }
        // });
    }
    //별 출력
    jQuery(document).ready(function($){
        $(function () {
            $(".my-rating-9").starRating({
                initialRating: null,
                disableAfterRate: null,
                onHover: function (currentIndex, currentRating, $el) {
                    //console.log('index: ', currentIndex, 'currentRating: ', currentRating, ' DOM element ', $el);
                    $('.live-rating').text(currentIndex);
                },
                onLeave: function (currentIndex, currentRating, $el) {
                    //console.log('index: ', currentIndex, 'currentRating: ', currentRating, ' DOM element ', $el);
                    $('.live-rating').text(currentRating);
                    $('.live-rating-form').val(currentRating);
                }
            });
        });
    })

    // 움직이는 포스터
    $(document).ready(function() {
        var isVisible = false;

        var currentPosition = parseInt($(".movingPoster").css("top"));
        $(window).scroll(function () {
            var position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환
            $(".movingPoster").stop().animate({"top": position + currentPosition + "px"}, 1000);
            if (checkVisible($('.footerCopyright')) && !isVisible) {
                $(".movingPoster").stop()
            }
        });

        function checkVisible(elm, eval) {
            eval = eval || "object visible";
            var viewportHeight = $(window).height(), // Viewport Height
                scrolltop = $(window).scrollTop(), // Scroll Top
                y = $(elm).offset().top,
                elementHeight = $(elm).height();

            if (eval == "object visible") return ((y < (viewportHeight + scrolltop)) && (y > (scrolltop - elementHeight)));
            if (eval == "above") return ((y < (viewportHeight + scrolltop)));
        }
    });




