package parser.impl.farpost;

import org.springframework.stereotype.Service;

import parser.engine.PauseMaker;
import parser.utils.Threads;

@Service
public class FarpostPauseMaker  implements PauseMaker{

    private final int numberOfObjectsBeforeLongPause=10;
   private final int longPauseDuration=10;
   private final int smallPauseDuration=3;

   private int currentCount=1;
   
   public FarpostPauseMaker(){



   }
   
    @Override
    public void pause() {
        if(currentCount>=numberOfObjectsBeforeLongPause){
            Threads.pauseThread(longPauseDuration, longPauseDuration+1);
            currentCount=0;
            return;
        }

        currentCount++;
        Threads.pauseThread(smallPauseDuration, smallPauseDuration+1);
              
    }
    

}
