import os

sizes = [128, 256, 512, 1024, 2048, 4096]
# threads = [128, 256]
threads = [1, 2, 4, 8, 16, 32, 64] #, 128, 256]

for size in sizes:
    for thr_num in threads:
        os.system("bsub -n " + str(thr_num // 8 + 1) + " -W 15 -o result/" + str(thr_num) + "_" + str(size) + ".out ./jacobi_2d " + str(thr_num) +  " " + str(size))