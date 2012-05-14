package ru.myrange.canvases;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 * This clas draws multiline text on the screen.
 * I took this from the www.mobilab.ru website and modernized it.
 */
public class MultiLineText {
    private int x,y,w,h,fsz,fst,fty; //Размер ограничивающего прямоугольника
    private int hStr; //Высота строки
    private int y0; //Положение верхнего края текста
    private int dy; //Шаг при прокрутке текста
    private int textheight; //Общая высота текста
    private Vector StringLines; 
    private Graphics g;
    private int gx,gy,gw,gh; //Исходная область
    
    public int getLastStringWidth()			// This routine returns the width of the last string
    {
    	int n = StringLines.size()-1;
    	int w = 0;
    	if (n > -1) {
    		w = g.getFont().stringWidth(StringLines.elementAt(n).toString());
    	}
    	return w;
    }

    public int getTextHeight()			    // This routine returns the height of the whole text
    {
    	return textheight;
    }
    
    public void  MoveDown()
    {
        if (textheight>h)
        {            
            y0=y0-dy;
            if (h-y0>textheight) {y0=h-textheight;}
        }
    }
    
    public void MoveUp()
    {
        if (textheight>h)
        {
            y0=y0+dy;
            if (y0>0){y0=0;}
        }
    }

    public void PageUp()
    {
        if (textheight>h)
        {
           y0 = y0 + h - hStr;	//Смещаем на высоту экрана минус высота одной строки
           if (y0>0){y0=0;} 
        }    
    }

    public void PageDown()
    {
        if (textheight>h)
        {
            y0 = y0 - h + hStr;	//Смещаем на высоту экрана минус высота одной строки
            if (h-y0>textheight) {y0=h-textheight;}
        }         
    }
    
    public void SetTextPar(
            int x, 
            int y,
            int width,
            int height,
            int dy,
            Font font,
            Graphics graph,
            String longString
            )
    {
        //Присваиваем значения внутренним переменным.
        this.x=x;
        this.y=y;
        this.w=width;
        this.h=height;
        this.dy=dy;
        this.fsz=font.getSize();
        this.fst=font.getStyle();
        this.fty=font.getFace();
        this.g=graph;
        //Запоминаем размеры текущей области для рисования
        gx=g.getClipX();
        gy=g.getClipY();
        gw=g.getClipWidth();
        gh=g.getClipHeight();
        //Устанавливаем параметры шрифта
        g.setFont(Font.getFont(fty, fst, fsz));
        hStr=g.getFont().getHeight(); //Высота строки в пикселях
        //*******************************************
        //* Разбиваем строку и создаем вектор строк *
        //*******************************************
        StringLines=new Vector(1);
        boolean isSlashN; //Есть ли символ перевода каретки
        int i0=0; //Номер прошлого найденного в строке пробела
        int i; //Номер последнего найденного в строке пробела
        int n; //Номер последнего найденного в строке символа перевода каретки
        int in=0; //Номер символа, соответствующего началу строки
        int j; //Ширина слова в пикселях до пробела
        int jw=0; //Ширина строки в пикселях
        int imax=longString.length(); //Длина строки в символах
        boolean isexit=true; //Флаг выхода
        y0=0;
        while (isexit)
        {
            i=longString.indexOf(" ", i0+1);
            n=longString.indexOf("\n", i0+1);
            if ( (i<=0) && (n<=0) ) {
                isexit=false;
            }
            n=( n > 0 ) ? n : imax;
            i=( i > 0 ) ? i : imax;
            if (n<i) {
            	isSlashN = true;
            	i = n;
            } else {
            	isSlashN = false;
            }
            j=g.getFont().stringWidth(longString.substring(i0,i));
            if ( (isSlashN) && (jw+j<w) )
        	{	// Символ перевода каретки
            	StringLines.addElement(longString.substring(in,n));
                in=n+1;
                jw=0;
                i0=n;
        	} else if (jw+j<w)
            {	// Слово умещается в текущей строке и нет символа перевода каретки
                jw=jw+j;
                i0=i;
            } else
            {	// Слово не умещается в текущей строке
            	if ((!longString.substring(i0,i0+1).equals("\n")) && (i0>in)) {
            		StringLines.addElement(longString.substring(in,i0));	// добавим предыдущую строчку, если она не заканчивалась \n
            	}
                in=i0+1;
                jw=j;
                if (j>w)
                {	// Ширина слова больше чем ширина области вывода
                  i=i0;
                  while (jw>w)
                  {
                    j=0; 
                    while (j<w)
                    {
                        i=i+1;
                        j=g.getFont().stringWidth(longString.substring(in,i));
                        /*if (LongString.substring(i-1,i).compareTo("\n") == 0) {
                        	break;
                        }*/
                    }
                    i=i-1;
                    j=g.getFont().stringWidth(longString.substring(in,i));
                    StringLines.addElement(longString.substring(in,i));
                    jw=jw-j;
                    i0=i; 
                    in=i;
                  }
                  jw=0; 
                }else
                {
                	i0=i;
                	if (isSlashN) {
                		StringLines.addElement(longString.substring(in,i0));
                		jw=0;
                		in=i0+1;
                	}
                } 
        	}
        }
        if (in <= imax) {
        	StringLines.addElement(longString.substring(in,imax));
        }
        textheight=StringLines.size()*hStr;
        y0=(-1)*textheight + h;	// Начинаем просмотр с последней записи
    }
    
    public void DrawBottomMultStr()     // Drow multistring in the bottom part of clip
    { 
       int y1; 
       g.setClip(x, y, w, h); //Ограничиваем область вывода 
       y1=y0;	// Начинаем просмотр с последней записи
       g.setFont(Font.getFont(fty, fst, fsz));
       for (int i=0;i<StringLines.size();i++)
       { 
           if ((y1+hStr)>0){
           g.drawString(StringLines.elementAt(i).toString(), x+1, y+y1, Graphics.LEFT|Graphics.TOP);
           }
           y1=y1+hStr; 
           if (y1>h){break;}
       }
       g.setClip(gx, gy, gw, gh);
       
    }

    public void DrawTopMultStr()    // Drow multistring in the top part of clip
    {
       int y1;
       g.setClip(x, y, w, h); //Ограничиваем область вывода
       y1=0;	// Начинаем просмотр с последней записи
       g.setFont(Font.getFont(fty, fst, fsz));
       for (int i=0;i<StringLines.size();i++)
       {
           if ((y1+hStr)>0){
           g.drawString(StringLines.elementAt(i).toString(), x+1, y+y1, Graphics.LEFT|Graphics.TOP);
           }
           y1=y1+hStr;
           if (y1>h){break;}
       }
       g.setClip(gx, gy, gw, gh);

    }
    
}
