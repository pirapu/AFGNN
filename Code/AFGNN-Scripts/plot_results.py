
import matplotlib.pyplot as plt
import numpy as np

def single_line_plot(x, y, xlabel, ylabel, title, output_location):
    plt.plot(x, y)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.title(title)
    plt.savefig(output_location)
    plt.show()
    plt.close()
    
def two_lines_plot(x1, y1, x2, y2, legend1, legend2, xlabel, ylabel, title, output_location):
    plt.plot(x1, y1, label = legend1)
    plt.plot(x2, y2, label = legend2)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.title(title)
    plt.legend() 
    plt.savefig(output_location)
    plt.show()
    plt.close()