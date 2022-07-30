// <ul id="banner"></ul>
// window.onload = function () {
//     let timer = setInterval(get_next, 3000)
//     let sz = new Array();
//     let szdiv = new Array()
//     var cur_ul = document.getElementById("banner");
//     for (let i = 1; i <= 5; i++) {
//         var cur_li = document.createElement("li");
//         var cur_img = document.createElement("img");
//
//         cur_img.src = "/images/banner" + i + ".jpg";
//         cur_img.style.width = "485px";
//         cur_img.style.height = "300px";
//         cur_li.appendChild(cur_img);
//
//         cur_li.onmouseenter = function () {
//             clearInterval(timer);
//         }
//         cur_li.onmouseleave = function () {
//             timer = setInterval(get_next, 3000)
//         }
//
//         if (i != 5) {
//             cur_li.id = 5 - i;
//         } else {
//             cur_li.id = 5;
//         }
//
//         cur_ul.appendChild(cur_li)
//         sz.push(cur_li);
//         sz[sz.length - 1].style.left = "0px";
//     }
//
//     let pre_img = document.createElement("img")
//     pre_img.src = "/images/prev.gif";
//     pre_img.style.position = "absolute";
//     pre_img.style.left = 0;
//     pre_img.style.top = 0;
//     pre_img.style.bottom = 0;
//     pre_img.style.margin = "auto"
//     pre_img.style.zIndex = 100;
//     cur_ul.appendChild(pre_img);
//
//     let nex_img = document.createElement("img")
//     nex_img.src = "/images/next.gif";
//     nex_img.style.position = "absolute";
//     nex_img.style.right = 0;
//     nex_img.style.top = 0;
//     nex_img.style.bottom = 0;
//     nex_img.style.margin = "auto"
//     nex_img.style.zIndex = 100;
//     cur_ul.appendChild(nex_img);
//
//     pre_img.onclick = function () {
//         clearInterval(timer);
//         get_pre();
//         timer = setInterval(get_next, 3000)
//     }
//
//     nex_img.onclick = function () {
//         clearInterval(timer);
//         get_next();
//         timer = setInterval(get_next, 3000)
//     }
//
//     let len = sz.length - 1;
//     sz[len - 2].style.left = "0px";
//     sz[len - 1].style.zIndex = 100;
//     sz[len - 1].style.left = "331px";
//     sz[len - 1].style.transform = "scale(1.2)";
//     sz[len].style.left = "650px";
//
//     for (let i = 0; i < szdiv.length; i++) {
//         szdiv[i].onmouseenter = function () {
//             clearInterval(timer);
//             let len1 = sz[len - 1].id;
//             let len2 = szdiv[i].name;
//             let dis = Math.max(len1, len2) - Math.min(len1, len2)
//             if (len1 > len2) {
//                 while (dis--)
//                     get_pre()
//             } else {
//                 while (dis--)
//                     get_next()
//             }
//             timer = setInterval(get_next,3000)
//         }
//     }
//
//
//     function get_pre() {
//         let give_up = sz[0];
//         sz.shift()
//         sz.push(give_up)
//         for (let i = 0; i < sz.length; i++) {
//             sz[i].style.zIndex = i;
//             sz[i].style.transform = "scale(1)"
//
//         }
//         sz[len - 2].style.left = "0px";
//         sz[len - 1].style.zIndex = 100
//         sz[len - 1].style.left = "331px";
//         sz[len - 1].style.transform = "scale(1.2)"
//         sz[len - 1].style.opacity = 1;
//         sz[len].style.left = "650px";
//
//         sync_szdiv()
//
//     }
//
//     function get_next() {
//         let give_up = sz[len];
//         sz.pop()
//         sz.unshift(give_up)
//         for (let i = 0; i < sz.length; i++) {
//             sz[i].style.zIndex = i;
//             sz[i].style.transform = "scale(1)"
//
//         }
//         sz[len - 2].style.left = "0px";
//         sz[len - 1].style.zIndex = 100
//         sz[len - 1].style.left = "331px";
//         sz[len - 1].style.transform = "scale(1.2)"
//         sz[len - 1].style.opacity = 1;
//         sz[len].style.left = "650px";
//     }
// }


var mySwiper = new Swiper('.swiper-container', {
    speed: 300,
    slidesPerView: '1',
    loop: true,
    autoplay: {
        delay: 3000,
        disableOnInteraction: false,
    },
    // 添加页面样式
    pagination: {
        el: '.swiper-pagination', // 要识别为页面字符的类
        clickable: true, //单击“页面”按钮后移动る
        type: 'bullets', // 形状类型
    },
    on: { // 事件
        slideChange: () => { // 幻灯片更改后将发生事件
            if (mySwiper.realIndex > 0) {
                mySwiper.params.autoplay.delay = 2700;
            }
        }
    }
})


