<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MathSpring | My Garden</title>
    <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
    <link rel="icon" type="image/png" href="/favicon-32x32.png" sizes="32x32">
    <link rel="icon" type="image/png" href="/favicon-16x16.png" sizes="16x16">
    <link rel="manifest" href="/manifest.json">
    <meta name="theme-color" content="#ffffff">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/common_new.css" rel="stylesheet" type="text/css" />
    <link href="css/Dashboard_new.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.css"/>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick-theme.css"/>
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/jchart.js"></script>
    <script src="js/slick.min.js"></script>
    <script src="js/vex.min.js"></script>
    <script>
        $(document).ready(function() {
            <c:forEach var="ts" items="${topicSummaries}">
                var topicState = "${ts.topicState}";
                var topicId = ${ts.topicId};
                var topicMastery = ${ts.mastery};
                var problemsDone = ${ts.problemsDone};
                var problemsSolved = ${ts.numProbsSolved};
                var totalProblems = ${ts.totalProblems};
                var problemsDoneWithEffort = ${ts.problemsDoneWithEffort};
                var SHINT_SOF_sequence = ${ts.SHINT_SOF_sequence};
                var SOF_SOF_sequence = ${ts.SOF_SOF_sequence};
                var neglectful_count = ${ts.neglectful_count};
                var studentState_disengaged = false;
                var chart = Chart;

                chart.init();
                chart.giveFeedbackAndPlant(
                    null,
                    "plant_"+topicId,
                    topicState,
                    studentState_disengaged,
                    topicMastery,
                    problemsDoneWithEffort,
                    SHINT_SOF_sequence,
                    SOF_SOF_sequence,
                    neglectful_count,
                    problemsDone,
                    problemsSolved);
            </c:forEach>

            $('.plant-garden').slick({
                slidesToShow: 6,
                dots: true,
                infinite: false
            });

            function showOverlay(title, numComplete, numTotal) {
                var percentage = numComplete / numTotal;
                console.log(percentage);
                var className = '';
                if (percentage <= 0.33) {
                    className = 'zero-progress';
                } else if (percentage > 0.33 && percentage <= 0.66) {
                    className = 'thirty-three-progress';
                } else {
                    className = 'sixty-six-progress';
                }

                var buttons = [];
                if (Number(numComplete) === 0) {
                    buttons = [
                        $.extend({}, vex.   dialog.buttons.NO, {
                            className: 'class-name',
                            text: 'Button1',
                            click: function($vexContent, event) {
                                window.location='http://www.example.com';
                            }
                        })
                    ];
                } else {
                    buttons = [
                        $.extend({}, vex.dialog.buttons.NO, {
                            className: 'class-name',
                            text: 'Button1',
                            click: function($vexContent, event) {
                                window.location='http://www.example.com';
                            }
                        }),
                        $.extend({}, vex.dialog.buttons.NO, {
                            className: 'class-name',
                            text: 'Button2',
                            click: function($vexContent, event) {
                                console.log("Hello world");
                            }
                        }),
                        $.extend({}, vex.dialog.buttons.NO, {
                            className: 'class-name',
                            text: 'Button3',
                            click: function($vexContent, event) {
                                console.log("Hello world");
                            }
                        })
                    ];
                }

                vex.dialog.open({
                    buttons: buttons,
                    input: [
                        '<style>',
                        '.vex-custom-field-wrapper {',
                        'margin: 1em 0;',
                        '}',
                        '.vex-custom-field-wrapper > label {',
                        'display: inline-block;',
                        'margin-bottom: .2em;',
                        '}',
                        '.topic-title {',
                        'font-weight: bold;',
                        '}',
                        '</style>',
                        '<div>',
                        '<h3 class="topic-title">' + title + '</h3>',
                        '<div class="visible num-questions">',
                        '<p>' + numComplete + ' / ' + numTotal + ' problems done</p>',
                        '</div>',
                        '<div class="progress overlay">',
                        '<div class="progress-bar ' + className + '" role="progressbar" aria-valuenow="' + percentage + '"',
                        'aria-valuemin="0" aria-valuemax="100" style="width:' + percentage * 100 + '%">',
                        '</div><!-- progress-bar -->',
                        '</div><!-- progress -->',
                        '</div><!-- section-panel -->',
                    ].join(''),
                    onSubmit: function() {
                    },
                });
            }
        });
    </script>
</head>
<body>

<!-- NAVIGATION BAR -->
<header class="site-header" role="banner">
    <div id="wrapper">
        <div class="navbar-header">
            <img class="logo" src="img/ms_mini_logo_new.svg">
        </div><!-- navbar-header -->

        <nav id="main_nav" class="nav navbar-nav navbar-right">
            <li class="dropdown dropdown-position custom-dropdown">
                <a href="#"
                    class="dropdown-toggle custom-dropdown-toggle"
                    data-toggle="dropdown"
                    role=button
                    aria-haspopup="true"
                    aria-expanded="false"
                >
                    <i><img src="img/avatar.svg" alt="Avatar"></i>
                    &nbsp;
                    ${studentFirstName}&nbsp;${studentLastName}
                    <span class="caret"></span>
                </a><!-- dropdown-toggle -->

                <ul class="dropdown-menu">
                    <li><a href="">HELP</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="TutorBrain?action=Logout&sessionId=${sessionId}&elapsedTime=${elapsedTime}&var=b">LOGOUT</a></li>
                </ul><!-- dropdown-menu -->
            </li><!-- dropdown -->
        </nav>
    </div><!-- wrapper -->
</header>

<section id="toggle-nav">
    <ul class="nav nav-tabs">
        <li class="navigation-tab active autofocus">
            <a data-toggle="tab" href="#my_garden">My Garden</a>
        </li>
        <li class="navigation-tab">
            <a data-toggle="tab"
               onclick="window.location='TutorBrain?action=navigation&from=sat_Hut&to=my_progress&elapsedTime=0&sessionId=${sessionId}'+ '&eventCounter=${eventCounter}' + '&topicId=-1&probId=${probId}&probElapsedTime=0&var=b'">My Progress</a>
        </li>
    </ul>
</section>

<div class="tab-content">
    <div id="my_garden" class="tab-pane fade in active">
        <!-- ALL POTS SECTION -->
        <section id="pots">
            <div class="container">
                <div class="row plant-garden">
                    <c:forEach var="ts" items="${topicSummaries}">
                        <c:set var="topicName" value="${ts.topicName}"/>
                        <c:set var="numProblemsDone" value="${ts.problemsDone}"/>
                        <c:set var="numTotalProblems" value="${ts.numProbsSolved}"/>
                        <c:set var="plantDiv" value="plant_${ts.topicId}"/>
                        <c:if test="${ts.problemsDone != 0}">
                            <div class="col-md-3 text-center topic"
                                 id=${plantDiv}
                                 topicTitle="${topicName}"
                                 totalProblem="${numTotalProblems}"
                                 completeProblem="${numProblemsDone}">
                                <%--<a href="#" id=${plantLink}><div id=${plantDiv}></div></a>--%>
                                <%--<img class="pot-image"--%>
                                     <%--onclick="showOverlay(--%>
                                        <%--this.parentElement.getAttribute('topicTitle'),--%>
                                        <%--this.parentElement.getAttribute('completeProblem'),--%>
                                        <%--this.parentElement.getAttribute('totalProblem')--%>
                                    <%--)"--%>
                                     <%--alt="Plant pot"--%>
                                <%-->--%>
                                <div class="talkbubble">
                                    <p>${topicName}</p>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
            </div><!-- container -->
        </section>
    </div><!-- student -->

    <div id="my_progress" class="tab-pane fade">
    </div>
</div><!-- tab-content -->

<!-- FIXED FOOTER, BUTTON FOR GETTING STARTED -->
<div id="big-working-button">
    <c:choose>
        <c:when test="${newSession}">
            <div class="container">
                <div class="row">
                    <h1>Start Practicing</h1>
                    <p>Have a good time working on these math problems</p>
                </div><!-- row -->
            </div><!-- container -->
            <a onclick="window.location='TutorBrain?action=EnterTutor&sessionId=${sessionId}'+'&elapsedTime=${elapsedTime}' + '&eventCounter=0&var=b'"><span class="link-spanner"></span></a>
        </c:when>
        <c:otherwise>
            <div class="container">
                <div class="row">
                    <h1>Continue Practicing</h1>
                    <p>Have a good time working on these math problems</p>
                </div><!-- row -->
            </div><!-- container -->
            <a onclick="window.location='TutorBrain?action=MPPReturnToHut&sessionId=${sessionId}'+'&elapsedTime=${elapsedTime}' + '&eventCounter=${eventCounter}' + '&probId=${probId}&topicId=-1' + '&learningCompanion=${learningCompanion}&var=b'"><span class="link-spanner"></span></a>
        </c:otherwise>
    </c:choose>

</div>


<!-- SCRIPT - LIBRARIES -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>
