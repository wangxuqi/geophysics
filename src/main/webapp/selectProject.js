var projects;
var lines;
var polylines = new Array();
var areas = new Array();
var infoWindows = new Array();

function getProjects() {
    $.getJSON("/geo/projects", function (get) {
        projects = get.data;
        var html = '<option value=0 class= "initialOption"> 选择测区 </option>';
        projects.forEach(function (val, key) {
            html += '<option value="' + val.projectId + '">' + val.name + '</option>';
        });
        $('#project').html(html);
    });
}

function getLinesByProjectId(projectId) {
    $.getJSON("/geo/projects/" + projectId + "/lines", function (get) {
        showLines(get);
    });
}

function getLinesByAreaId(projectId, surveyAreaId) {
    $.getJSON("/geo/projects/" + projectId + "/areas/" + surveyAreaId + "/lines", function (get) {
        showLines(get);
    });
}

function getAreasByProjectId(projectId) {
    $.getJSON("/geo/projects/" + projectId + "/areas", function (get) {
        showAreas(get);
    });
}

function getAreasByMethodId(methodId) {
    var projectId = $("#project option:selected").val();
    $.getJSON("/geo/projects/" + projectId + "/method/" + methodId + "/areas", function (get) {
        showAreas(get);
    });
}

function showProject(projectId) {
    if (projectId != "0") {
        $("#method").removeAttr("disabled");
    } else {
        $("#method").attr("disabled", "disabled");
    }
    $(".initialOption").attr("disabled", "disabled");
    $("#method").val(0);
    var polygonArr;
    projects.forEach(function (val, key) {
        if (val.projectId == projectId) {
            polygonArr = val.polygon;
            polygon = new AMap.Polygon({
                path: eval(polygonArr), //设置多边形边界路径
                strokeColor: "#0000ff", //线颜色
                strokeOpacity: 0.2, //线透明度
                strokeWeight: 3, //线宽
                fillColor: "#f5deb3", //填充色
                fillOpacity: 0.35 //填充透明度
            });
            map.clearMap();
            polygon.setMap(map);
            map.setFitView(polygon);
            //getLinesByProjectId(projectId);
            getAreasByProjectId(projectId);
            AMap.event.addListener(polygon, 'click', (function (polygon) {
                return function () {
                    map.remove(polylines);
                    map.remove(infoWindows);
                    map.setFitView(polygon);
                    getAreasByProjectId(projectId);
                }
            })(polygon));
        }
    });
}


//根据物探方法选择测区填充颜色
function judgeAreaColor(methodId) {
    if (methodId == 1) {
        return "#003300";
    } else if (methodId == 2) {
        return "#FF6633";
    } else if (methodId == 3) {
        return "#000099";
    } else if (methodId == 4) {
        return "#3366FF";
    }
}

function showLines(get) {
    var data = get.data;
    polylines = [];
    data.forEach(function (val, key) {
        lineArr = [
            [val.startPointLon, val.startPointLat],
            [val.endPointLon, val.endPointLat]
        ];
        polyline = new AMap.Polyline({
            path: lineArr, //设置线覆盖物路径
            strokeColor: "#3366FF", //线颜色
            strokeOpacity: 1, //线透明度
            strokeWeight: 2, //线宽
            strokeStyle: "solid", //线样式
            strokeDasharray: [10, 5], //补充线样式
            extData: val,
        });
        AMap.event.addListener(polyline, 'mouseover', (function (polyline) {
            return function () {
                polyline.setOptions({
                    strokeColor: "#33FF66",
                    strokeWeight: 4
                });
            }
        })(polyline));
        AMap.event.addListener(polyline, 'mouseout', (function (polyline) {
            return function () {
                polyline.setOptions({
                    strokeColor: "#3366FF",
                    strokeWeight: 2
                });
            }
        })(polyline));
        AMap.event.addListener(polyline, 'click', (function (polyline) {
            return function () {
                lon = (polyline.getExtData().startPointLon + polyline.getExtData().endPointLon) / 2;
                lat = (polyline.getExtData().startPointLat + polyline.getExtData().endPointLat) / 2;
                console.log(polyline.getExtData());
                //实例化信息窗体
                var title = '测线详情',
                    content = [];
                content.push("<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134'>");
                content.push("测线距离: " + polyline.getExtData().distance + "  包含测点个数: " + polyline.getExtData().number);
                content.push("采集时间: " + polyline.getExtData().collectTime + "   采集人:    " + polyline.getExtData().collector);
                content.push("处理时间: " + polyline.getExtData().processedTime + " 处理人:    " + polyline.getExtData().processor);
                content.push("原始数据: " + polyline.getExtData().originDataPath);
                content.push("<a href='http://ditu.amap.com/detail/B000A8URXB?citycode=110105'>处理后数据:    " + polyline.getExtData().processedDataPath + "</a>");
                content.push("测线剖面图:    " + polyline.getExtData().processedDataImagePath);
//                content.push("<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134'>地址：北京市朝阳区阜通东大街6号院3号楼东北8.3公里");
//                content.push("电话：010-64733333");
//                content.push("<a href='http://ditu.amap.com/detail/B000A8URXB?citycode=110105'>详细信息</a>");
                var infoWindow = new AMap.InfoWindow({
                    isCustom: true,  //使用自定义窗体
                    content: createInfoWindow(title, content.join("<br/>")),
                    offset: new AMap.Pixel(16, -20)
                });
                infoWindow.open(map, [lon, lat]);
                infoWindows.push(infoWindow);
                console.log(lon);
                console.log(lat);

            }
        })(polyline));
        polylines.push(polyline);
    });
    map.add(polylines);
}

function showAreas(get) {
    map.remove(areas);
    map.remove(polylines);
    areas = [];
    var data = get.data;
    data.forEach(function (val, key) {
        filledColor = judgeAreaColor(val.methodId);
        polygonArr = val.polygon;
        var area = new AMap.Polygon({
            path: eval(polygonArr), //设置线覆盖物路径
            strokeColor: "#3366FF", //线颜色
            strokeOpacity: 0.5, //线透明度
            strokeWeight: 2, //线宽
            fillColor: filledColor, //填充色
            fillOpacity: 0.35, //填充透明度
            extData: val,
        });
        AMap.event.addListener(area, 'click', (function (area) {
            return function () {
                map.setFitView(area);
                map.remove(areas);
                map.add(area);
                map.remove(polylines);
                map.remove(infoWindows);
                getLinesByAreaId(val.projectId, val.surveyAreaId);
            }
        })(area));
        areas.push(area);
    });
    map.add(areas);
    map.setFitView(areas);
}


//构建自定义信息窗体
function createInfoWindow(title, content) {
    var info = document.createElement("div");
    info.className = "info";

    //可以通过下面的方式修改自定义窗体的宽高
    info.style.width = "400px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "http://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "http://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}

//关闭信息窗体
function closeInfoWindow() {
    map.clearInfoWindow();
}