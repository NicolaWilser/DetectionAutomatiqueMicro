package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import dataStruct.AcquisitionData;
import dataStruct.FormatterLab;




public class AcquTree extends JPanel{

		private static final long serialVersionUID = 7126562625087308771L;
		private JTree tree;
	    private DefaultMutableTreeNode top;
	    private DefaultTreeModel treeModel;


	    	    
	    	class NodeRenderer implements TreeCellRenderer {
	    		  private JPanel leafRenderer;
		    	  private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
		    	  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		    		  
			    	    Component returnValue;
			    	    if (((DefaultMutableTreeNode) value).getUserObject() instanceof AcquisitionData){		    	 
			    	      FormatterLab frm = new FormatterLab(3,2);	
			    	      leafRenderer = new JPanel();
			    	      AcquisitionData data  = (AcquisitionData) ((DefaultMutableTreeNode) value).getUserObject();	
			    	      
			    	      JLabel name = new JLabel("Name: " + data.getName());
			    	      leafRenderer.setLayout(new BoxLayout(leafRenderer,BoxLayout.Y_AXIS));
			    	      leafRenderer.add(name);
			    	      JLabel roil = new JLabel("ROI: " + data.getRoi().toString());
			    	      leafRenderer.add(roil);
			    	      if (data.is2DType()){
			    	    	  
			    	    	  JLabel pos = new JLabel("Time Lapse Data @ : " + data.getPos().toString());
			    	    	  JLabel nb = new JLabel("Number of takes: " + data.getNum2d());
			    	    	  JLabel temp = new JLabel("Tempo: " + data.getTempo() + " ms");
			    	    	  
			    	    	  leafRenderer.add(pos);
			    	    	  leafRenderer.add(nb);
			    	    	  leafRenderer.add(temp);

			    	      } else {
			    	    	  
			    	    	  JLabel pos = new JLabel("3D Data @ : " + data.getPos().toString());
			    	    	  JLabel minm = new JLabel("Z from: " + frm.format(data.getzMin()) + " to: " + frm.format(data.getzMax()));
					    	  JLabel stp = new JLabel("Step of : " + data.getStep() + " µm");
					    	  leafRenderer.add(pos);
					    	  leafRenderer.add(minm);
				    	      leafRenderer.add(stp);
			    	      }

			    	      JLabel exp = new JLabel("Exposure: " + data.getExposure() + " ms");
			    	      JLabel eml = new JLabel("Emission: " + data.getEmission());
			    	      JLabel exl = new JLabel("Excitation: " + data.getExcitation());
			    	      leafRenderer.add(exp);
			    	      leafRenderer.add(eml);
			    	      leafRenderer.add(exl);
			    	      returnValue = leafRenderer;
			    	    } else {
			    	      returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
			    	          leaf, row, hasFocus);
			    	    }
			    	    return returnValue;
			    	  }
			
	    
	    	}
	    	

	    public AcquTree( Dimension dim) {
	        super(new GridLayout(1,0));
	        top = new DefaultMutableTreeNode("Aquisition Series");
	        treeModel = new DefaultTreeModel(top);
	        tree = new JTree(treeModel);
	        tree.setEditable(false);
	        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	        tree.setShowsRootHandles(true);
	        
	        JScrollPane treeView = new JScrollPane(tree);
	        treeView.setPreferredSize(dim) ;
	        this.add(treeView);
	        tree.putClientProperty("JTree.lineStyle","Angled");
	        NodeRenderer renderer = new NodeRenderer();
	        tree.setCellRenderer(renderer);
	    }
	    
	    public AcquisitionData[] getWorkLine(){
	    	Object rootNode = treeModel.getRoot();
	    	int count = treeModel.getChildCount(rootNode);
	    	AcquisitionData line[] = new AcquisitionData[count];
	    	for (int i = 0; i < count; i++){
	    		Object tmpNode = treeModel.getChild(rootNode, i);
	    		DefaultMutableTreeNode datav= (DefaultMutableTreeNode) treeModel.getChild(tmpNode, 0);
	    		line[i] = (AcquisitionData) datav.getUserObject();
	    	}
	    	return line;
	    	
	    }
	   
	    public void removeSelectedData(){
	    	TreePath[] selection = tree.getSelectionPaths();
	    	if (selection != null) {
	    		for (TreePath tp : selection){
	    			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(tp.getLastPathComponent());
	    			MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	    			if (parent != null) {
	    				treeModel.removeNodeFromParent(currentNode);
	    			}
	    		}
	    	}     
	    }
	    
	    
	    
	    public void removeData(int index){
	    	Object rootNode = treeModel.getRoot();
	    	int count = treeModel.getChildCount(rootNode);
	    	if (index < count){
	    		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(treeModel.getChild(rootNode, index));
	    		MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	    		if (parent != null) {
	    			treeModel.removeNodeFromParent(currentNode);
	    		}
	    	}    
	    }
	    
	    
	    
	    public void clearAll(){
	    	DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
	    	rootNode.removeAllChildren();
	        treeModel.reload();
	    }
	    public void addAData(AcquisitionData data,boolean shouldBeVisible){

	    	DefaultMutableTreeNode dp = new DefaultMutableTreeNode(data.getName());
	    	treeModel.insertNodeInto(dp, top, top.getChildCount());
	    	DefaultMutableTreeNode dp2 = new DefaultMutableTreeNode(data);
	    	treeModel.insertNodeInto(dp2, dp, dp.getChildCount());
	    	 if (shouldBeVisible) {
	             tree.scrollPathToVisible(new TreePath(dp2.getPath()));
	         }
	    }
	}