<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <title>Agent</title>
    <meta charset="utf-8"/>
<@css href="ui.datepicker.min.css,public.css,foundation.min.css,searchportal.css" rel="stylesheet" type="text/css"/>
<@js src="json2.js,jquery.min.js,jquery.form.js,common.js,ui.datepicker.js,jquery.validate.js"/>
<@js src="foundation/foundation.js"/>
<@js src="foundation/foundation.reveal.js"/>
<@js src="foundation/foundation.topbar.js"/>
<@js src="foundation/foundation.forms.js"/>
    <script type="text/javascript">
        golbalRootUrl = "<@url value='/' />";
    </script>
</head>
<body>
<nav class="top-bar">
    <ul class="title-area">
        <li class="name">
            <h1><a href="<@url value='/home'/>/"><span style="color:White;">Agent</span></a></h1>
        </li>
    </ul>
    <ul class="right">
        <li class="has-form"><a style="margin-left: 5px;" class="small radius button" href="<@url value='/signOut'/>">Logout</a></li>
    </ul>
</nav>

<@body/>
</body>
<script>$(document).foundation();</script>