
    $(window).load(function(){
        $('#loadingModal').modal('show');
        setTimeout(function(){$('#loadingModal').modal('hide');},500);
    });

    var canvas = document.getElementById('canvas');
    var stage = new JTopo.Stage(canvas);
    var scene = new JTopo.Scene(stage);

    // 方形节点
    function newNode(x, y, w, h, text){
        var node = new JTopo.Node(text);
        node.setLocation(x+Xaxis, y);
        node.setSize(w, h);
        node.textPosition = 'Middle_Center';
        node.borderRadius = 10;
        node.font = '12pt 微软雅黑';
        node.fillColor = '255, 101, 101';
        scene.add(node);
        return node;
    }

    // 圆形节点'254, 152, 0'
    function newCircleNode(x, y, w, h, text,name,color,big){
        var node = new JTopo.CircleNode(text);
        if (big == 'big'){
            w = 175;
            h = 175;
        }
        node.setLocation(x, y);
        node.setSize(w, h);
        node.textPosition = 'Middle_Center';
        node.font = '11pt 微软雅黑';
        node.fontColor = 'black';
        if (color == 'yellow'){
            node.fillColor = '254, 152, 0';
        }else{
            node.fillColor = '102, 204, 150';
        }

        //文字节点
        var msgNode = new JTopo.TextNode(name);
        msgNode.zIndex++;
        if (name.length < 4){
            msgNode.setBound(x+45, y+20);
        }else{
            msgNode.setBound(x+25, y+20);
        }

        scene.add(msgNode);
        scene.add(node);
        return node;
    }

    // 曲线
    function newCurveLink(nodeA, nodeZ, text){
        var link = new JTopo.CurveLink(nodeA, nodeZ, text);
        link.lineWidth = 3; // 线宽
        scene.add(link);
        return link;
    }

    var mainNode = newNode(450,300, 100, 100,"同人模型")
    for (var i=0; i<position.length; i++){
        var childNode = newCircleNode(position[i][0],position[i][1], 115, 115,position[i][2],position[i][3],position[i][4],position[i][5]);
        var link = newCurveLink(childNode, mainNode);
    }
