
/**
 * Written by Mostafa Mohamed Ali
 * A scheduling strategy Based Shortest Job First (SJF)
 */
import java.util.*;

public class SJFScheduler extends BasicScheduler {

	public static void sort(Queue<BasicPCB> q) {

		BasicPCB[] b = new BasicPCB[q.size()];
		int size = q.size();

		for (int i = 0; i < size; i++) {
			b[i] = q.poll();
		}
		// Access each array element
		for (int i = 0; i < size - 1; i++) {

			// Compare elements
			for (int j = 0; j < size - i - 1; j++) {

				if (b[j].getTotalLines() > b[j + 1].getTotalLines()) {
					BasicPCB temp = b[j];
					b[j] = b[j + 1];
					b[j + 1] = temp;
				}
			}
		}

		for (int i = 0; i < size; i++) {
			q.add(b[i]);
		}
	}

	@Override
	public void updateRunningProcess() {
		sort(readyQ);
		if (runningProcess == null) {
			dispatch();
			return;
		}
		runningProcess.nextLine();
		runningProcess.setPriority(runningProcess.getTotalLines());
		if (runningProcess.hasCompleted()) {
			runningProcess.setCompletionTick(tickCount);
			waitingTimeSum += (runningProcess.getCompletionTick() - runningProcess.getArrivalTick());
			dispatch();
		}
	}

	@Override
	public void addProcess(BasicPCB p) {
		totalProcesses++;
		// set periority in total lines
		p.setPriority(p.getTotalLines());
		readyQ.add(p);
	}
}