ul#nav {
    display:block;
    float:left;
    font-family:Trebuchet MS,sans-serif;
    font-size:0;
	/*
	padding:5px 5px 5px 0;
	*/
	}
ul#nav,ul#nav ul {
    list-style:none;
    margin:0;
}
ul#nav,ul#nav .subs {
/*
    background-color:#444;
    border:1px solid #454545;

    border-radius:9px;
    -moz-border-radius:9px;
    -webkit-border-radius:9px;
*/
	}
ul#nav .subs {
    /*background-color:#fff;*/
	background-color:#ebebeb;
    /*border:2px solid #222;*/
	border:thin solid #cccccc;
    display:none;
    float:left;
    left:0;
    padding:0 6px 6px;
    position:absolute;
    top:100%;
    width:160px;

    border-radius:7px;
    -moz-border-radius:7px;
    -webkit-border-radius:7px;
}

ul#nav .ssubs {
    /*background-color:#fff;*/
	background-color:#ebebeb;
    /*border:2px solid #222;*/
	border:thin solid #cccccc;
    display:none;
    float:left;
    left:0;
    padding:0 6px 6px;
    position:absolute;
    top:100%;
    width:130px;

    border-radius:7px;
    -moz-border-radius:7px;
    -webkit-border-radius:7px;
}
ul#nav li:hover>* {
    display:block;
}
ul#nav li:hover {
    position:relative;
}
ul#nav ul .subs {
    left:100%;
    position:absolute;
    top:0;
}
ul#nav ul {
    padding:0 5px 5px;
}
ul#nav .col {
    float:left;
    width:50%;
}
ul#nav .scol {
    float:left;
    width:25%;
}
ul#nav li {
    display:block;
    float:left;
    font-size:0;
    white-space:nowrap;
}
ul#nav>li,ul#nav li {
    /*
	margin:0 0 0 5px;
	*/
	margin:2px 0;
}
ul#nav ul>li {
    margin:5px 0 0;
}
ul#nav a:active,ul#nav a:focus {
    outline-style:none;
}
ul#nav a {
    border-style:none;
    border-width:0;
    color:#181818;
    cursor:pointer;
    display:block;
    font-size:13px;
    font-weight:bold;
    /*
	padding:8px 18px;
	*/
	padding:2px 18px;
    text-align:left;
    text-decoration:none;
    text-shadow:#fff 0 1px 1px;
    vertical-align:middle;
}
ul#nav ul li {
    float:none;
    margin:6px 0 0;
}
ul#nav ul a {
    /*background-color:#fff;*/
	background-color:#ebebeb;
    border-color:#efefef;
    border-style:solid;
    border-width:0 0 1px;
    color:#000;
    font-size:11px;
    padding:4px;
    text-align:left;
    text-decoration:none;
    text-shadow:#fff 0 0 0;
	
    border-radius:0;
    -moz-border-radius:0;
    -webkit-border-radius:0;
}
ul#nav li:hover>a {
    border-style:none;
    /*color:#fff;*/
	color:green;
    font-size:13px;
    font-weight:bold;
    text-decoration:none;
    /*text-shadow:#181818 0 1px 1px;*/
	text-shadow:#ebebeb 0 1px 1px;
}
ul#nav img {
    border:none;
    margin-right:8px;
    vertical-align:middle;
	margin-bottom:1px;
}
ul#nav span {
    background-position:right center;
    background-repeat:no-repeat;
    display:block;
    overflow:visible;
    padding-right:0;
}
ul#nav ul span {
    background-image:url("../images/arrow.png");
    padding-right:20px;
}
ul#nav ul li:hover>a {
    /*
	border-color:#444;
    */
	border-style:solid;
    /*
	color:#444;
    */
	color:green;
	font-size:11px;
    text-decoration:none;
    text-shadow:#fff 0 0 0;
}
ul#nav > li >a {
    background-color:transparent;
    height:25px;
    line-height:25px;

    border-radius:11px;
    -moz-border-radius:11px;
    -webkit-border-radius:11px;
}
ul#nav > li:hover > a {
    /*background-color:#313638;*/
	background-color:#ebebeb;
	border:thin solid #cccccc;
    line-height:25px;
}

.aactive
{
    background-color:transparent;

    border-radius:11px;
    -moz-border-radius:11px;
    -webkit-border-radius:11px;
	
	background-color:#ebebeb;
	border:thin solid #cccccc;	
}

.col li, .scol li{
	width:120px;
}

.ssubs{
	z-index:10000;
}