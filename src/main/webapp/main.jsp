<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>Geophysics</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <script src="selectProject.js"></script>
    <script src="//cdn.bootcss.com/jquery/3.0.0-beta1/jquery.js"></script>
    <link rel="stylesheet" href="customStyle.css"/>
</head>
<body onload="getProjects()">

<div id="container">
</div>
<div id="panel">
    <h3>Geophysics Visualization</h3>
    <hr/>
    <br/>
    <form id="form_projects">&nbsp;&nbsp;地物工程:
        <select name="project" id="project" onchange="showProject(this.options[this.options.selectedIndex].value)">
        </select>
    </form>
    <form id="form_methods"> &nbsp;&nbsp;使用方法:
        <select name="method" id="method" onchange="getAreasByMethodId(this.options[this.options.selectedIndex].value)"
                disabled="disabled">
            <option value="0" class="initialOption">选择方法</option>
            <option value="1">雷达</option>
            <option value="2">地震</option>
            <option value="3">高密度电</option>
            <option value="4">重力</option>
        </select>
    </form>
    <hr/>
    <div>
        <h3>测区详情</h3>
        <table>
            <tr>
                <td>开始时间</td>
                <td>结束时间</td>
                <td>负责人</td>
            </tr>
            <tr>
                <td>400</td>
                <td>500</td>
                <td>600</td>
            </tr>
        </table>
    </div>
</div>
<div id="myPageTop">
    <table>
        <tr>
            <td class="column2">
                <label>左击获取经纬度：</label>
            </td>
        </tr>
        <tr>
            <td class="column2">
                <input type="text" readonly="true" id="lnglat">
            </td>
        </tr>
    </table>
</div>
<script>
    var map = new AMap.Map('container', {
        resizeEnable: true,
        width: "300px",
        height: "180px",
        center: [120.044378, 30.375964],
        zoom: 18,
        mapStyle: "blue"
    });
    var clickEventListener = map.on('dblclick', function(e) {
        document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat()
    });

</script>
</body>
</html>
