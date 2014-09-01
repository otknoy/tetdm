//
// Core Program for TETDM
// MakeCluster.java Version 0.44
// Copyright(C) 2011-2014 TETDM CORE MEMBER ALL RIGHTS RESERVED.
// This program is provided under the license.
//


package source;

import source.TextData.*;


public class MakeCluster {
  public class Cluster {
    public int elements[];			//Elements of a cluster
    public int num;				//Number of elements

    Cluster() {
      num = 0;
    }
  }

  public Cluster cl[];		//Cluster information
  public int cluster_num;		//Number of clusters

  class Node {
    int link_num;
    int cluster_id;

    Node() {
      link_num = 0;
      cluster_id = -1;
    }
  }

  Node dn[];
  int node_num;

  int lonelyNodeNumber;

  public RelationalData rd;

  int max_loop;
  int BaseCluster, ConnectCluster;

  public MakeCluster(RelationalData r) {
    rd = r;

    if (rd.number == 0) {
      return;
    }

    node_num = rd.number;
    dn = new Node[node_num];
    for (int i=0; i<node_num; i++) {
      dn[i] = new Node();
    }

    lonelyNodeNumber = 0;

    for (int i=0; i<node_num; i++) {
      dn[i].link_num = rd.linkNumber[i] - 1;	// reduce self-link

      if (dn[i].link_num > 0) {
        dn[i].cluster_id = i;
      } else {
        dn[i].cluster_id = -1;		// Without links, not counted as a cluster.
        lonelyNodeNumber++;
      }
    }
//		System.out.println("Lonely Node Number = "+lonelyNodeNumber+"/"+node_num);

    cluster_num = node_num;
    numbering();
    newman_clustering(cluster_num);
    numbering();
//		print_result();
  }

  void newman_clustering(int max_loop) {
    for (int k=0; k<max_loop; k++)
      if (GetMaxDeltaQ()) {
        for (int i=0; i<node_num; i++)
          if (dn[i].cluster_id == ConnectCluster) {
            dn[i].cluster_id = BaseCluster;
          }
        numbering();
      }
  }

  //Find combination that the value delta become the maximum
  boolean GetMaxDeltaQ() {
    double maxdelta=0, delta;

    BaseCluster = ConnectCluster = -1;

    for (int i=0; i<cluster_num; i++)
      for (int j=i+1; j<cluster_num; j++) {
        delta = calc_delta(i,j);

        if (delta > maxdelta) {
          maxdelta = delta;
          BaseCluster = i;
          ConnectCluster = j;
        }
      }

    if (BaseCluster!=-1 && ConnectCluster!=-1) {
      return true;
    } else {
      return false;
    }
  }

  //Evaluation Values calculation when a base cluster and an another cluster are connected
  double calc_delta(int base, int joint) {
    double to_base=0, to_joint=0, base_joint=0;

    for (int i=0; i<node_num; i++) {
      if (dn[i].cluster_id == base) {
        to_base += dn[i].link_num;
      }

      if (dn[i].cluster_id == joint) {
        to_joint += dn[i].link_num;
      }
    }

    to_base /= 2.0;					// Reduce double count
    to_joint /= 2.0;				// Reduce double count

    //Count nubmer of links inside/outside a cluster
    for (int i=0; i<node_num; i++) {
      if (dn[i].cluster_id != base && dn[i].cluster_id != joint) {
        continue;
      }

      for (int j=i+1; j<node_num; j++) {
        if (dn[j].cluster_id != base && dn[j].cluster_id != joint) {
          continue;
        }

        if (rd.link[i][j])
          if (dn[i].cluster_id != dn[j].cluster_id) {
            base_joint+=1.0;  //Number of links between base and joint
          }
      }
    }

    return (base_joint - to_base*to_joint/rd.totalLinkNumber);
  }

  void numbering() {
    boolean cluster_check[] = new boolean[cluster_num];
    int new_clnum[] = new int[cluster_num];
    int count;

    //Count number of clusters
    for (int c=0; c<cluster_num; c++) {
      cluster_check[c] = false;
    }

    for (int i=0; i<node_num; i++)
      if (dn[i].cluster_id != -1) {
        cluster_check[dn[i].cluster_id] = true;
      }

    count = 0;
    for (int c=0; c<cluster_num; c++)
      if (cluster_check[c] == true) {
        new_clnum[c] = count++;
      }

    //Re assignment of the ID of each cluster
    for (int i=0; i<node_num; i++)
      if (dn[i].cluster_id != -1) {
        dn[i].cluster_id = new_clnum[dn[i].cluster_id];
      }

    cluster_num = count;

    CL();//Make cluster information
  }

  void print_result() {
    System.out.println("Cluster Num = "+cluster_num);
    for (int i=0; i<cluster_num; i++) {
      System.out.println("--- Cluster "+(i+1)+" ----");
      for (int j=0; j<cl[i].num; j++) {
        System.out.print(rd.name[cl[i].elements[j]]+" ");
      }
      System.out.println();
    }
  }

  //Create cluster information, SHOULD be used after number of cluster is decided
  void CL() {
    cl = new Cluster[cluster_num];

    for (int c=0; c<cluster_num; c++) {
      cl[c] = new Cluster();
    }


    for (int i=0; i<node_num; i++)
      if (dn[i].cluster_id != -1) {
        cl[dn[i].cluster_id].num++;
      }

    for (int c=0; c<cluster_num; c++) {
      cl[c].elements = new int[cl[c].num];
      cl[c].num = 0;
    }

    for (int i=0; i<node_num; i++)
      if (dn[i].cluster_id != -1) {
        cl[dn[i].cluster_id].elements[cl[dn[i].cluster_id].num++] = i;
      }
  }
}
