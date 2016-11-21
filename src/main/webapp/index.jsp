<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>折线、多边形、圆</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
<div id="container"></div>
<script>
    var map = new AMap.Map('container', {
        resizeEnable: true,
        center: [120.044376,30.377361],
        zoom: 18
    });


	var lineArr = [
	[120.040376,30.379361],
	[120.048262,30.374622]
	];

	var polyline = new AMap.Polyline({
        path:lineArr,          //设置线覆盖物路径
        strokeColor: "#3366FF", //线颜色
        strokeOpacity: 1,       //线透明度
        strokeWeight: 5,        //线宽
        strokeStyle: "solid",   //线样式
        strokeDasharray: [10, 5] //补充线样式
    });
	polyline.setMap(map);



    var polygonArr = [
	[120.044764,30.37825],
	[120.047918,30.377991],
	[120.047403,30.376704],
	[120.04456,30.377139],
	[120.044764,30.37825]
	];
    var  polygon = new AMap.Polygon({
        path: polygonArr,//设置多边形边界路径
        strokeColor: "#003322", //线颜色
        strokeOpacity: 0.2, //线透明度
		strokeStyle:"dashed",
        strokeWeight: 3,    //线宽
        fillColor: "#889123", //填充色
        fillOpacity: 0.35//填充透明度
    });
	polygon.setExtData("1");
    polygon.setMap(map);
	AMap.event.addListener(polygon, "click",openInfo);
	 function openInfo() {
    //构建信息窗体中显示的内容
    var info = [];
    info.push("<div><img src=\" http://webapi.amap.com/images/autonavi.png \"/> ");
    info.push("<div style=\"padding:0px 0px 0px 4px;\"><b>良渚工程</b>");
    info.push("开始时间：2016-3-11");
    info.push("结束时间：2016-3-25");
	info.push("Id:"+polygon.getExtData()+"</div></div>");

    infoWindow = new AMap.InfoWindow({
        content: info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容
    });
    infoWindow.open(map, [120.044376,30.377361]);
 }
</script>
</body>
</html>