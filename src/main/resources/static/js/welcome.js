let sayingOne = document.querySelector(".saying-one");
let sayingTwo = document.querySelector(".saying-two");
let fadeAndMove = [
  {
    opacity: 0,
    transform: `translateY(-20px)`,
  },
  {
    opacity: 1,
    transform: `translateY(0px)`,
  },
];

let titleTiming = {
  duration: 2000,
  easing: "ease-in-out",
};

const titleChange = sayingOne.animate(fadeAndMove, titleTiming);

let expand = [
  {
    letterSpacing: "-0.5em",
    opacity: 0,
  },
  {
    letterSpacing: "initial",
    opacity: 1,
  },
];

let subTitleTiming = {
  duration: titleChange.effect.getComputedTiming().duration / 2,
  easing: "ease-in-out",
};

const subTitleChange = sayingTwo.animate(expand, subTitleTiming);
subTitleChange.pause();

let sayingTwoContent = [
    'Keep hunger，keep foolis !',
    'Do one thing at a time, and do well !',
    'Never forget to say “thanks” !',
    'Whatever is worth doing is worth doing well !',
    'Never put off what you can do today until tomorrow !',
    'Keep good men company and you shall be of the number !',
    'Knowledge makes humble, ignorance makes proud !',
    'Learn to walk before you run !',
    'You cannot improve your past, but you can improve your future. Once time is wasted, life is wasted !',
    'Don not aim for success if you want it; just do what you love and believe in, and it will come naturally !'
]

// 延时展示
setTimeout(() => {
    sayingTwo.innerHTML = sayingTwoContent[0];
    subTitleChange.play();
}, 3000);

let index = 1;
setInterval(() => {
    sayingTwo.innerHTML = sayingTwoContent[index];
    subTitleChange.play();
    index++;
    if(index == 9){
        index = 0;
    }
}, 20000);

// 监听鼠标点击展示
// document.addEventListener("click", () => {
//   if (subTitleChange.playState !== "finished") {
//     subTitleChange.play();
//   }
// });

// 点击链接逻辑处理
let qqBtn = document.querySelector(".qq-btn");
let weixingBtn = document.querySelector(".weixing-btn");
let githubBtn = document.querySelector(".github-btn");
 
let qqCode = document.querySelector(".qq-code");
let weixingCode = document.querySelector(".weixing-code");
let githubCode = document.querySelector(".github-code");

let qqSum = 0;
let weixingSum = 0;
let githubSum = 0;
qqBtn.onclick = function(){
    qqSum++;
    if(qqSum % 2 == 0){
        qqCode.setAttribute("style","display:none");
    } else {
        qqCode.setAttribute("style","display:block");
    }
};

weixingBtn.onclick = function(){
    weixingSum++;
    if(weixingSum % 2 == 0){
        weixingCode.setAttribute("style","display:none");
    } else {
        weixingCode.setAttribute("style","display:block");
    }
};

githubBtn.onclick = function(){
    githubSum++;
    if(githubSum % 2 == 0){
        githubCode.setAttribute("style","display:none");
    } else {
        githubCode.setAttribute("style","display:block");
    }
};