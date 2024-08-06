package parser.impl.avito;

import parser.engine.PauseMaker;
import parser.utils.Threads;

public class AvitoPauseMaker implements PauseMaker {
    @Override
    public void pause() {
        Threads.pauseThread(4, 6);

    }
}
