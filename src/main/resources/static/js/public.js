// 设置滚到指定位置固定
function changePosition(id,height){
    var obj = document.getElementById(id);
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    if(scrollTop < height){
        obj.style.position = 'relative';
        obj.style.top = '0';
    }else{
        obj.style.position = 'fixed';
        obj.style.top = '-190px';
        obj.style.right = '190px';
    }
}