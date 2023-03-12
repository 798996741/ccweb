
    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess(x,y,radius,process,backColor,proColor,fontColor){
        var canvas = document.getElementById('myCanvas');
        if (canvas.getContext) {
            var cts = canvas.getContext('2d');
        }else{
            return;
        }

        cts.beginPath();
        // �����ƶ���Բ��
        cts.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts.arc(x, y, radius, 0, Math.PI * 2, false);
        cts.closePath();
        // �����ɫ
        cts.fillStyle = backColor;
        cts.fill();

        cts.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts.closePath();
        cts.fillStyle = proColor;
        cts.fill();

        //��䱳����ɫ
        cts.beginPath();
        cts.moveTo(x, y);
        cts.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts.closePath();
        cts.fillStyle = 'rgba(255,255,255,1)';
        cts.fill();

        //���м�д��
        cts.font = "bold 22pt Arial";
        cts.fillStyle = fontColor;
        cts.textAlign = 'center';
        cts.textBaseline = 'middle';
        cts.moveTo(x, y);
        cts.fillText(process+"%", x, y);

    }
bfb = 0;
time=0;
function Start(){
    DrowProcess(60,60,55,bfb,'#ddd','#8ccc38','#8ccc38');
    t = setTimeout(Start,5);
    if(bfb>=75){
        clearTimeout(t);
        bfb=0;
        return;
    }
    bfb+=1;
}

Start();



    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess1(x,y,radius,process,backColor,proColor,fontColor){
        var canvas1 = document.getElementById('myCanvas1');
        if (canvas1.getContext) {
            var cts1 = canvas1.getContext('2d');
        }else{
            return;
        }

        cts1.beginPath();
        // �����ƶ���Բ��
        cts1.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts1.arc(x, y, radius, 0, Math.PI * 2, false);
        cts1.closePath();
        // �����ɫ
        cts1.fillStyle = backColor;
        cts1.fill();

        cts1.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts1.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts1.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts1.closePath();
        cts1.fillStyle = proColor;
        cts1.fill();

        //��䱳����ɫ
        cts1.beginPath();
        cts1.moveTo(x, y);
        cts1.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts1.closePath();
        cts1.fillStyle = 'rgba(255,255,255,1)';
        cts1.fill();

        //���м�д��
        cts1.font = "bold 22pt Arial";
        cts1.fillStyle = fontColor;
        cts1.textAlign = 'center';
        cts1.textBaseline = 'middle';
        cts1.moveTo(x, y);
        cts1.fillText(process+"%", x, y);

    }
    bfb = 0;
    time=0;
    function Start1(){
        DrowProcess1(60,60,55,bfb,'#ddd','#f7921e','#f7921e');
        t = setTimeout(Start1,5);
        if(bfb>=25){
            clearTimeout(t);
            bfb=0;
            return;
        }
        bfb+=1;
    }

    Start1();



    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess2(x,y,radius,process,backColor,proColor,fontColor){
        var canvas2 = document.getElementById('myCanvas2');
        if (canvas2.getContext) {
            var cts2 = canvas2.getContext('2d');
        }else{
            return;
        }

        cts2.beginPath();
        // �����ƶ���Բ��
        cts2.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts2.arc(x, y, radius, 0, Math.PI * 2, false);
        cts2.closePath();
        // �����ɫ
        cts2.fillStyle = backColor;
        cts2.fill();

        cts2.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts2.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts2.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts2.closePath();
        cts2.fillStyle = proColor;
        cts2.fill();

        //��䱳����ɫ
        cts2.beginPath();
        cts2.moveTo(x, y);
        cts2.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts2.closePath();
        cts2.fillStyle = 'rgba(255,255,255,1)';
        cts2.fill();

        //���м�д��
        cts2.font = "bold 22pt Arial";
        cts2.fillStyle = fontColor;
        cts2.textAlign = 'center';
        cts2.textBaseline = 'middle';
        cts2.moveTo(x, y);
        cts2.fillText(process+"%", x, y);

    }
    bfb = 0;
    time=0;
    function Start2(){
        DrowProcess2(60,60,55,bfb,'#ddd','#e6e6e6','#e6e6e6');
        t = setTimeout(Start2,5);
        if(bfb>=0){
            clearTimeout(t);
            bfb=0;
            return;
        }
        bfb+=1;
    }

    Start2();





    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess3(x,y,radius,process,backColor,proColor,fontColor){
        var canvas3 = document.getElementById('myCanvas3');
        if (canvas3.getContext) {
            var cts3 = canvas3.getContext('2d');
        }else{
            return;
        }

        cts3.beginPath();
        // �����ƶ���Բ��
        cts3.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts3.arc(x, y, radius, 0, Math.PI * 2, false);
        cts3.closePath();
        // �����ɫ
        cts3.fillStyle = backColor;
        cts3.fill();

        cts3.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts3.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts3.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts3.closePath();
        cts3.fillStyle = proColor;
        cts3.fill();

        //��䱳����ɫ
        cts3.beginPath();
        cts3.moveTo(x, y);
        cts3.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts3.closePath();
        cts3.fillStyle = 'rgba(255,255,255,1)';
        cts3.fill();

        //���м�д��
        cts3.font = "bold 22pt Arial";
        cts3.fillStyle = fontColor;
        cts3.textAlign = 'center';
        cts3.textBaseline = 'middle';
        cts3.moveTo(x, y);
        cts3.fillText(process+"%", x, y);

    }
    bfb = 0;
    time=0;
    function Start3(){
        DrowProcess3(60,60,55,bfb,'#ddd','#8ccc38','#8ccc38');
        t = setTimeout(Start3,5);
        if(bfb>=75){
            clearTimeout(t);
            bfb=0;
            return;
        }
        bfb+=1;
    }

    Start3();








    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess4(x,y,radius,process,backColor,proColor,fontColor){
        var canvas4 = document.getElementById('myCanvas4');
        if (canvas4.getContext) {
            var cts4 = canvas4.getContext('2d');
        }else{
            return;
        }

        cts4.beginPath();
        // �����ƶ���Բ��
        cts4.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts4.arc(x, y, radius, 0, Math.PI * 2, false);
        cts4.closePath();
        // �����ɫ
        cts4.fillStyle = backColor;
        cts4.fill();

        cts4.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts4.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts4.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts4.closePath();
        cts4.fillStyle = proColor;
        cts4.fill();

        //��䱳����ɫ
        cts4.beginPath();
        cts4.moveTo(x, y);
        cts4.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts4.closePath();
        cts4.fillStyle = 'rgba(255,255,255,1)';
        cts4.fill();

        //���м�д��
        cts4.font = "bold 22pt Arial";
        cts4.fillStyle = fontColor;
        cts4.textAlign = 'center';
        cts4.textBaseline = 'middle';
        cts4.moveTo(x, y);
        cts4.fillText(process+"%", x, y);

    }
    bfb = 0;
    time=0;
    function Start4(){
        DrowProcess4(60,60,55,bfb,'#ddd','#fd9421','#fd9421');
        t = setTimeout(Start4,5);
        if(bfb>=25){
            clearTimeout(t);
            bfb=0;
            return;
        }
        bfb+=1;
    }

    Start4();




    //x,y ����,radius �뾶,process �ٷֱ�,backColor ������ɫ, proColor ������ɫ, fontColor ����������ɫ
    function DrowProcess5(x,y,radius,process,backColor,proColor,fontColor){
        var canvas5 = document.getElementById('myCanvas5');
        if (canvas5.getContext) {
            var cts5 = canvas5.getContext('2d');
        }else{
            return;
        }

        cts5.beginPath();
        // �����ƶ���Բ��
        cts5.moveTo(x, y);
        // ��Բ,Բ����24,24,�뾶24,�ӽǶ�0��ʼ,����2PI����,���һ�������Ƿ���˳ʱ�뻹����ʱ��
        cts5.arc(x, y, radius, 0, Math.PI * 2, false);
        cts5.closePath();
        // �����ɫ
        cts5.fillStyle = backColor;
        cts5.fill();

        cts5.beginPath();
        // �����ε�ʱ���ⲽ����Ҫ,���ʲ���Բ�Ļ������Ĳ�������
        cts5.moveTo(x, y);
        // �������ԲΨһ������������,������Բ,��������
        cts5.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts5.closePath();
        cts5.fillStyle = proColor;
        cts5.fill();

        //��䱳����ɫ
        cts5.beginPath();
        cts5.moveTo(x, y);
        cts5.arc(x, y, radius - (radius * 0.26), 0, Math.PI * 2, true);
        cts5.closePath();
        cts5.fillStyle = 'rgba(255,255,255,1)';
        cts5.fill();

        //���м�д��
        cts5.font = "bold 22pt Arial";
        cts5.fillStyle = fontColor;
        cts5.textAlign = 'center';
        cts5.textBaseline = 'middle';
        cts5.moveTo(x, y);
        cts5.fillText(process+"%", x, y);

    }
    bfb = 0;
    time=0;
    function Start5(){
        DrowProcess5(60,60,55,bfb,'#ddd','#fd9421','#fd9421');
        t = setTimeout(Start5,5);
        if(bfb>=25){
            clearTimeout(t);
            bfb=0;
            return;
        }
        bfb+=1;
    }

    Start5();

