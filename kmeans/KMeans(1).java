package kmeans;

import java.util.Scanner;

public class KMeans {
	Scanner sc = new Scanner(System.in);
	int k;
	int num;
	double ele[][];
	int old_clust_num_of_ele[];
	int clust_num_of_ele[];
	int num_of_ele_in_cluster[];
	double centroid_value[][];
	double temp_dist;
	double min_dist;
	int min_index;
	int pass=1;
	
	public double dist(int i1,int i2) {
		return Math.sqrt(Math.pow((ele[i1][0]-centroid_value[i2][0]), 2) + Math.pow((ele[i1][1] - centroid_value[i2][1]), 2));
	}
	
	public boolean done() {
		int e;
		for(e=0;e<num;e++) {
			if(clust_num_of_ele[e] != old_clust_num_of_ele[e]) {
				return false;
			}
		}
		return true;
	}
	
	public KMeans() {
		int i,j;
		System.out.print("Enter Number of Cluster: ");
		k = sc.nextInt();
		System.out.print("Enter Number of Elements: ");
		num = sc.nextInt();
		
		if(k>num) {
			System.out.println("Number of Clusters should be less than Elements");
			return;
		}
		
		ele = new double[num][2];
		clust_num_of_ele = new int[num];
		num_of_ele_in_cluster = new int[k];
		centroid_value = new double[k][2];
		old_clust_num_of_ele = new int[num];
		
		System.out.println("Enter x & y:");
		for(i=0;i<num;i++) {
			System.out.print(i+": ");
			ele[i][0] = sc.nextDouble();
			ele[i][1] = sc.nextDouble();
		}
		
		for(i=0;i<k;i++) {
			centroid_value[i][0] = ele[i][0];
			centroid_value[i][1] = ele[i][1];
			clust_num_of_ele[i] = i;
		}
		
		do {
			System.out.println("------ Pass "+pass+++" -------");
			for(i=0;i<k;i++) {
				num_of_ele_in_cluster[i] = 0;
			}
			
			for(i=0;i<num;i++) {
				old_clust_num_of_ele[i] = clust_num_of_ele[i];
				min_dist = dist(i,0);
				min_index = 0;
				for(j=1;j<k;j++) {
					temp_dist = dist(i,j);
					if(temp_dist<min_dist) {
						min_dist = temp_dist;
						min_index = j;
					}
				}
				clust_num_of_ele[i] = min_index;
				num_of_ele_in_cluster[min_index]++;
			}
			
			for(i=0;i<k;i++) {
				centroid_value[i][0] = centroid_value[i][1] = 0.0;
			}
			
			for(i=0;i<k;i++) {
				System.out.println("Cluster "+i);
				for(j=0;j<num;j++) {
					if(clust_num_of_ele[j] == i) {
						System.out.println(j+ ": ("+ele[j][0]+","+ele[j][1]+")");
						centroid_value[i][0] += ele[j][0];
						centroid_value[i][1] += ele[j][1];
					}
				}
				centroid_value[i][0] = (double)centroid_value[i][0]/num_of_ele_in_cluster[i];
				centroid_value[i][1] = (double)centroid_value[i][1]/num_of_ele_in_cluster[i];
				System.out.println("\tCentroid: ("+centroid_value[i][0]+","+centroid_value[i][1]+")");
			}
		}while(!done());
		
	}
	public static void main(String[] args) {
		new KMeans();
	}
}
