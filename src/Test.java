public class Test {
	
	public static String zeroRaceCase() {
        FastTrack ft = new FastTrack();

        // Start 10 threads
        for (int i = 1; i <= 10; i++) {
            ft.startThread(i);
        }

        // Create 100 variables
        String[] variables = new String[100];
        for (int i = 0; i < 100; i++) {
            variables[i] = "var" + i;
        }

        // Create tasks for 10 threads
        Runnable[] tasks = new Runnable[10];
        for (int i = 0; i < 10; i++) {
            int threadId = i + 1;
            int startIdx = i * 10;
            tasks[i] = () -> {
                // Veri yarýþý oluþturmayan iþ parçacýðý
                for (int j = startIdx; j < startIdx + 10; j++) {
                    // Her thread farklý deðiþkenler üzerinde çalýþýyor
                    ft.write(threadId, variables[j]);
                    ft.read(threadId, variables[j]);
                }
            };
        }

        // Start and join the threads
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Return the total number of data races detected
        return "Total number of data races detected: " + ft.getRaceCount();
    }
	public String thirtyRaceCase() {
		 FastTrack ft = new FastTrack();

	        // Start 10 threads
	        for (int i = 1; i <= 10; i++) {
	            ft.startThread(i);
	        }

	        // Create 100 variables
	        String[] variables = new String[100];
	        for (int i = 0; i < 100; i++) {
	            variables[i] = "var" + i;
	        }

	        // Create tasks for 10 threads
	        Runnable[] tasks = new Runnable[10];

	        // 3 threads that will cause data races
	        for (int i = 0; i < 3; i++) {
	            int threadId = i + 1;
	            tasks[i] = () -> {
	                for (int j = 0; j < 10; j++) {
	                     //(3 thread * 10 actions each = 30 races)
	                    ft.write(threadId, variables[j]);
	                    ft.read(threadId, variables[j]);
	                }
	            };
	        }

	        // 7 threads that won't cause data races
	        for (int i = 3; i < 10; i++) {
	            int threadId = i + 1;
	            tasks[i] = () -> {
	                for (int j = 10; j < 20; j++) {
	                    try {
	                        Thread.sleep(100);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }
	            };
	        }

	        // Start and join the threads
	        Thread[] threads = new Thread[10];
	        for (int i = 0; i < 10; i++) {
	            threads[i] = new Thread(tasks[i]);
	            threads[i].start();
	        }

	        for (int i = 0; i < 10; i++) {
	            try {
	                threads[i].join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        // Print the total number of data races detected
	        return "Total number of data races detected: " + ft.getRaceCount();
	}
	
	public String fiftyRaceCase() {
		FastTrack ft = new FastTrack();

        // Start 10 threads
        for (int i = 1; i <= 10; i++) {
            ft.startThread(i);
        }

        // Create 100 variables
        String[] variables = new String[100];
        for (int i = 0; i < 100; i++) {
            variables[i] = "var" + i;
        }

        // Create tasks for 10 threads
        Runnable[] tasks = new Runnable[10];

        // 3 threads that will cause data races
        for (int i = 0; i < 5; i++) {
            int threadId = i + 1;
            tasks[i] = () -> {
                for (int j = 0; j < 10; j++) {
                    // Yarýþ oluþturan iþ parçacýðý (5 thread * 10 actions each = 10 races)
                    ft.write(threadId, variables[j]);
                    ft.read(threadId, variables[j]);
                }
            };
        }

        // 7 threads that won't cause data races
        for (int i = 5; i < 10; i++) {
            int threadId = i + 1;
            tasks[i] = () -> {
                for (int j = 10; j < 20; j++) {
                	// Herhangi bir deðiþkene yazma ve okuma yapmayalým
                    // Sadece uyusunlar
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        // Start and join the threads
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the total number of data races detected
        return "Total number of data races detected: " + ft.getRaceCount();
	}
	
	public String raceTrackCase() {
		FastTrack ft = new FastTrack();

        // Start 10 threads
        for (int i = 1; i <= 10; i++) {
            ft.startThread(i);
        }

        // Create 100 variables
        String[] variables = new String[100];
        for (int i = 0; i < 100; i++) {
            variables[i] = "var" + i;
        }

        // Create tasks for 10 threads
        Runnable[] tasks = new Runnable[10];

        // 5 threads that will cause data races (10 actions each)
        for (int i = 0; i < 5; i++) {
            int threadId = i + 1;
            tasks[i] = () -> {
                for (int j = 0; j < 10; j++) {
                    // Yarýþ oluþturan iþ parçacýðý (5 thread * 10 actions each = 50 races)
                    ft.write(threadId, variables[j]);
                    ft.read(threadId, variables[j]);
                }
            };
        }

        // 1 thread that will cause 3 data races
        tasks[5] = () -> {
            for (int j = 0; j < 3; j++) {
                // Yarýþ oluþturan iþ parçacýðý (1 thread * 3 actions = 3 races)
                ft.write(6, variables[j]);
                ft.read(6, variables[j]);
            }
            // Kalan iþlemler yarýþ oluþturmayan
            for (int j = 3; j < 10; j++) {
            	try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 4 threads that won't cause data races (10 actions each)
        for (int i = 6; i < 10; i++) {
            int threadId = i + 1;
            tasks[i] = () -> {
                for (int j = 10; j < 20; j++) {
                	try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        // Start and join the threads
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the total number of data races detected
        return "Total number of data races detected: " + ft.getRaceCount();
	}
}
