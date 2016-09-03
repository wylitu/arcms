//加载动画效果
$(window).load(function(){
    $('#loadingModal').modal('show');
    setTimeout(function(){$('#loadingModal').modal('hide');},500);
});


//jtopo画图
var canvas = document.getElementById('canvas');
var stage = new JTopo.Stage(canvas);
var scene = new JTopo.Scene(stage);

// 方形节点
function newNode(flag,x, y, w, h, text,color){
    var node = new JTopo.Node(text);
    node.setLocation(x, y);
    node.setSize(w, h);
    node.textPosition = 'Middle_Center';
    node.borderRadius = 10;
    node.font = '12pt 微软雅黑';
    if (color != null){
        node.fillColor = color;
    }else{
        node.fillColor = '166, 216, 255';
    }
    scene.add(node);

    if (flag){
        var userCount = newText(x+20,y,"5000人",12,'0,0,255',true);
        var percent = newText(x+100,y+5,'占比20%',8,'0,0,0');
        var fans = newText(x+20,y+30,'成为粉丝：4875人',10,'0,0,0');
    }
    return node;
}

// 简单连线
function newLink(nodeA, nodeZ,color){
    var link = new JTopo.Link(nodeA, nodeZ);
    link.lineWidth = 3; // 线宽
    link.arrowsRadius = 15; //箭头大小
    link.bundleOffset = 60; // 折线拐角处的长度
    link.bundleGap = 20; // 线条之间的间隔
    link.textOffsetY = 3; // 文本偏移量（向下3个像素）
    if (color == "color1"){
        link.strokeColor = '247,10,26';
    }else if (color == "color2"){
        link.strokeColor = '242,214,73';
    }; // 线条颜色随机
    scene.add(link);
    return link;
}

function newText(x,y,text,size,color,href){
    //文字节点
    var msgNode;
    if (href == true){
        msgNode = new JTopo.LinkNode(text);
        msgNode.href = "javascript:freshUrl('/sf-web/knowCustomer/userList')";
    }else{
        msgNode = new JTopo.TextNode(text);
    }
    msgNode.zIndex++;
    msgNode.setBound(x,y);
    if (color != null){
        msgNode.fontColor = color;
    }else{
        msgNode.fontColor = '0,0,0';
    }
    msgNode.font = size+'pt 微软雅黑';
    scene.add(msgNode);
}

var node1_1 = newNode(false,20,130,10,10,'','66,73,89');
var node1_2 = newNode(false,1200,130,10,10,'','66,73,89');
var line1 = newLink(node1_2,node1_1,'color1');

var node2_1 = newNode(false,180,90,10,10,'','66,73,89');
var node2_2 = newNode(false,180,700,10,10,'','66,73,89');
var line2 = newLink(node2_2,node2_1,'color2');

var title = newText(80,0,'用户贡献度模型UVM',17,'255,255,255');
var lit_title = newText(160,40,'价值',14,'176,194,58');
var x_title_1 = newText(270,90,'淘系',14,'242,214,73');
var x_title_2 = newText(550,90,'京东',14,'242,214,73');
var x_title_3 = newText(820,90,'有赞',14,'242,214,73');
var y_title_1 = newText(25,165,'强关系  V>90',14,'189,204,242');
var y_title_2 = newText(10,255,'强关系  80<V<90',14,'189,204,242');
var y_title_3 = newText(10,345,'强关系  70<V<80',14,'189,204,242');
var y_title_4 = newText(10,455,'强关系  60<V<70',14,'189,204,242');
var y_title_5 = newText(25,555,'强关系  V<60',14,'189,204,242');



for (var i=0; i<5; i++){
    for (var j=0; j<3; j++){
        var node = newNode(true,230+270*j,150+100*i,170,70);
    }
}




