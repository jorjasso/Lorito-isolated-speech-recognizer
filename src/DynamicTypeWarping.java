//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//implementa el dynamic time warping

public class DynamicTypeWarping{
	
	int opcion;
	int r;       //tamaño de ventana de ajuste
	public DynamicTypeWarping(int r){
		this.r=r;
		
		}
	
	public void opcionDistancia(int opcion){
		
		this.opcion=opcion;
		}
	
	public  double matching( double [][] A, double [][] B){
		
		int I=A.length;
		int J=B.length;
		
		double [][] d  = new double[I][J] ;  // tabla de distancias para programacion dinamica
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		int i,j;
		double d1=1000000;
		double d2=1000000; 
		double d3=1000000;
		double suma=0;
		
		
		/***********/
		i=0;j=0;
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		 do{
			i=i+1;
			if(i>=j+r){
				j=j+1;
				if(j>=J){
					
				return suma/(I+J);
					}
				else{
					i=j-r;
					}	
				
				}
			
			else{
				if(i>=0){
					if(i<I){
	
					//	calcular DWT
					/*******************/
					if (j!=0){          d1 =d[i][j-1]   + distancia (A[i],B[j-1]); }
					if (i!=0&&j!=0){	d2 =d[i-1][j-1]  +2*distancia (A[i-1],B[j-1]);}
					if (i!=0){          d3 =d[i-1][j]   + distancia (A[i-1],B[j]);  }

					if(i!=0||j!=0){
					
	//					System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
						d[i][j]= min(d1,d2,d3);
	//					System.out.println("d["+i+"]["+j+"]"+d[i][j]);
						d1=1000000;
						d2=1000000;
						d3=1000000;
						
					}			
									
  				     suma=suma+d[i][j];
					/*******************/
						
						}//del if
					
				 }//de if
				
				}	//del else
			
			
			}while(true);
		
		

		/***********/
		
/*		for (i=0;i<A.length;i++ ){
			for(j=0;j<B.length;j++){
				
				if (j!=0){          d1 =d[i][j-1]   + distancia (A[i],B[j-1]); }
				if (i!=0&&j!=0){	d2 =d[i-1][j-1]  +2*distancia (A[i-1],B[j-1]);}
				if (i!=0){          d3 =d[i-1][j]   + distancia (A[i-1],B[j]);  }
				
				
					if(i!=0||j!=0){
					
//					System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
					d[i][j]= min(d1,d2,d3);
//					System.out.println("d["+i+"]["+j+"]"+d[i][j]);
					d1=1000000;
					d2=1000000;
					d3=1000000;
					
					}			
									
				    suma=suma+d[i][j];
				    
				
				} //del for
				
//				System.out.println(" ");
			
			} //del for
		
*/		
		
//		 System.out.println(" distancia white and neely = "+suma);
//		 System.out.println("           normalizada= "+suma/(A.length+B.length));
		 
		
//		return suma/(A.length+B.length);
		}
		
	public  double matching_p_1( double [][] A, double [][] B){
		
		int I=A.length;
		int J=B.length;
		double [][] d  = new double[A.length][B.length] ;  // tabla de distancias para programacion dinamica
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		int i,j;
		double d1=1000000;
		double d2=1000000; 
		double d3=1000000;
		double suma=0;
		
		/********************/
		i=0;j=0;
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		do{
			i=i+1;
			if(i>=j+r){
				j=j+1;
				if(j>=J){
					
					return suma/(I+J);
				
					}
				else{
					i=j-r;
					}	
				
				}
			
			else{
				if(i>=0){
					if(i<I){
					
					//	calcular DWT
					/*****************/
					if (i!=0&&j!=0&&j!=1){	d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);}
					if(i>=2&&j>=2){
				  	     d1 =d[i-1][j-2]  +  2*distancia (A[i-1],B[j-2])+distancia(A[i-1],B[j-1]);
	                     d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
	            		 d3 =d[i-2][j-1]   + 2*distancia (A[i-2],B[j-1])+distancia(A[i-1],B[j-1]);
					}
				
					// si estan en los bordes
					if (i==0&&j!=0){    d1 =d[i][j-1]   + distancia (A[i],B[j-1]); }
					if (i!=0&&j==0){    d3 =d[i-1][j]   + distancia (A[i-1],B[j]);  }
					
				    if (i==1&&j==1){
					      d1 =d[i-1][j]   + distancia (A[i-1],B[j]);  
					      d2 =d[i-1][j-1] +2*distancia (A[i-1],B[j-1]);
					      d3 =d[i][j-1]   + distancia (A[i],B[j-1]); 
					                    
					    
				    					}
				    				
				    if(i==1&&j>1){
			    	
			    	      d1 =d[i-1][j-2]  +  2*distancia (A[i-1],B[j-2])+distancia(A[i-1],B[j-1]);
				          d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
				          d3 =d[i-1][j]   + distancia (A[i-1],B[j]); 
	
			    	        }					
	
					if(i>1&&j==1){
			    	      d1 =d[i][j-1]  +  distancia (A[i],B[j-1]); 
				          d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
				          d3 =d[i-2][j-1]   + distancia (A[i-2],B[j-1])+2*distancia (A[i-2],B[j-1])+distancia(A[i-1],B[j-1]);			    	
			    	        }
				
					if(i!=0||j!=0){
					
		//				System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
						d[i][j]= min(d1,d2,d3);
		//				System.out.println("d["+i+"]["+j+"]"+d[i][j]);
						d1=1000000;
						d2=1000000;
						d3=1000000;
						
					}					

				    suma=suma+d[i][j];

					/*****************/
						
						}//del if
					
				 }//de if
				
				}	//del else
			
			
			}while(true);
		/********************/
		
		
/*		for (i=0;i<A.length;i++ ){
			for(j=0;j<B.length;j++){
				
	//			if (i!=0&&j!=0&&j!=1){System.out.println("entroooo if (i!=0&&j!=0&&j!=1)");	d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);}
	//			if (i!=0&&i!=1&&j!=0&&j!=1){ System.out.println("entroooo if (i!=0&&j!=0)");         d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  }
	//			if (i!=0&&i!=1&&j!=0){  System.out.println("entroooo if (i!=0&&i!=1&&j!=0)");        d3 =d[i-2][j-1]   + 2*distancia (A[i-1],B[j])+distancia(A[i],B[j]); }
				
				if(i>=2&&j>=2){
//					System.out.println("entroooo if i>=2&&j>=2");
				  	     d1 =d[i-1][j-2]  +  2*distancia (A[i-1],B[j-2])+distancia(A[i-1],B[j-1]);
	                     d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
	            		 d3 =d[i-2][j-1]   + 2*distancia (A[i-2],B[j-1])+distancia(A[i-1],B[j-1]);
				}
				
				// si estan en los bordes
				if (i==0&&j!=0){    d1 =d[i][j-1]   + distancia (A[i],B[j-1]); }
				if (i!=0&&j==0){    d3 =d[i-1][j]   + distancia (A[i-1],B[j]);  }
				
			    if (i==1&&j==1){
	//		    	System.out.println("entroooo if (i==1&&j==1)");
				                    d1 =d[i-1][j]   + distancia (A[i-1],B[j]);  
				                    d2 =d[i-1][j-1] +2*distancia (A[i-1],B[j-1]);
				                    d3 =d[i][j-1]   + distancia (A[i],B[j-1]); 
				                    
				    
			    					}
			    				
			    if(i==1&&j>1){
	//		    	System.out.println("entroooo if(i==1&&j>1)");
			    	
			    	                 d1 =d[i-1][j-2]  +  2*distancia (A[i-1],B[j-2])+distancia(A[i-1],B[j-1]);
				                     d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
				                     d3 =d[i-1][j]   + distancia (A[i-1],B[j]); 
				                     
				             //        System.out.println("d[0][2]"+d[0][2]);
	//		    	System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
			    	        }					
			    					
				
				if(i>1&&j==1){
			    	                 d1 =d[i][j-1]  +  distancia (A[i],B[j-1]); 
				                     d2 =d[i-1][j-1]   + 2*distancia (A[i-1],B[j-1]);  
				                     d3 =d[i-2][j-1]   + distancia (A[i-2],B[j-1])+2*distancia (A[i-2],B[j-1])+distancia(A[i-1],B[j-1]);
			    	
			    	        }
				
				
				if(i!=0||j!=0){
					
	//				System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
					d[i][j]= min(d1,d2,d3);
	//				System.out.println("d["+i+"]["+j+"]"+d[i][j]);
					d1=1000000;
					d2=1000000;
					d3=1000000;
					
					}					
//				 else {System.out.println("no entro en "+i+" "+j);}   
				    
				    
	//			    System.out.println("d["+i+"]["+j+"] = "+d[i][j]+" min ( d1 = "+d1+" d2 = "+d2+" d3 = "+d3+")");
				    suma=suma+d[i][j];
				    
				
				} // del for
				
	//			System.out.println(" ");
			
			} //del for
*/		
		
//		 System.out.println(" distancia  matching simetrico p=1"+suma);
//		 System.out.println("            normalizada= "+suma/(A.length+B.length));
//		return suma/(A.length+B.length);
	
		}		
		
		
	public  double simetrico_p_0( double [][] A, double [][] B){
		
		int I=A.length;
		int J=B.length;
		
		double [][] d  = new double[A.length][B.length] ;  // tabla de distancias para programacion dinamica
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		int i,j;
		double d1=1000000;
		double d2=1000000; 
		double d3=1000000;
		double suma=0;
		
		
		/***********/
		i=0;j=0;
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		 do{
			i=i+1;
			if(i>=j+r){
				j=j+1;
				if(j>=J){
					
				return suma/(I+J);
					}
				else{
					i=j-r;
					}	
				
				}
			
			else{
				if(i>=0){
					if(i<I){
	
					//	calcular DWT
					/***********/
						if (j!=0){          d1 =d[i][j-1]   + distancia (A[i],B[j]); }
						if (i!=0&&j!=0){	d2 =d[i-1][j-1]  +2*distancia (A[i],B[j]);}
						if (i!=0){          d3 =d[i-1][j]   + distancia (A[i],B[j]);  }
				
						if(i!=0||j!=0){
						
	//					System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
						d[i][j]= min(d1,d2,d3);
	//					System.out.println("d["+i+"]["+j+"]"+d[i][j]);
						d1=1000000;
						d2=1000000;
						d3=1000000;
						
						}			
									
				      suma=suma+d[i][j];
					/***********/	
						}//del if
					
				 }//de if
				
				}	//del else
			
			
			}while(true);
		/***********/
	/*	for (i=0;i<A.length;i++ ){
			for(j=0;j<B.length;j++){
				
				if (j!=0){          d1 =d[i][j-1]   + distancia (A[i],B[j]); }
				if (i!=0&&j!=0){	d2 =d[i-1][j-1]  +2*distancia (A[i],B[j]);}
				if (i!=0){          d3 =d[i-1][j]   + distancia (A[i],B[j]);  }
				
				
					if(i!=0||j!=0){
					
//					System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
					d[i][j]= min(d1,d2,d3);
//					System.out.println("d["+i+"]["+j+"]"+d[i][j]);
					d1=1000000;
					d2=1000000;
					d3=1000000;
					
					}			
									
				    suma=suma+d[i][j];
				    
				
				} //end for
				
//				System.out.println(" ");
			
			} //end for
	*/	
		
//		 System.out.println(" distancia sakoe and chiba simetrico p=0"+suma);
//	     System.out.println("           normalizada= "+suma/(A.length+B.length));
//		return suma/(A.length+B.length);
		}	
		
		
	public  double simetrico_p_1( double [][] A, double [][] B){
		
		int I=A.length;
		int J=B.length;
		double [][] d  = new double[A.length][B.length] ;  // tabla de distancias para programacion dinamica
		
		d[0][0]=2*distancia(A[0],B[0]);
		
		int i,j;
		double d1=1000000;
		double d2=1000000; 
		double d3=1000000;
		double suma=0;
		
		
		/****************/
		  i=0;j=0;
		
		 d[0][0]=2*distancia(A[0],B[0]);
		
		 do{
			i=i+1;
			if(i>=j+r){
				j=j+1;
				if(j>=J){
					
				return suma/(I+J);
		//		break;
					}
				else{
					i=j-r;
					}	
				
				}
			
			else{
				if(i>=0){
					if(i<I){
	
					//	calcular DWT
					/*************/
					if (i!=0&&j!=0&&j!=1){	d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);}
					if(i>=2&&j>=2){

				  	     d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);
	                     d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
	            		 d3 =d[i-2][j-1]   + 2*distancia (A[i-1],B[j])+distancia(A[i],B[j]);
				     }
				
					// si estan en los bordes
					if (i==0&&j!=0){    d1 =d[i][j-1]   + distancia (A[i],B[j]); }
					if (i!=0&&j==0){    d3 =d[i-1][j]   + distancia (A[i],B[j]);  }
					
				    if (i==1&&j==1){

                         d1 =d[i-1][j]   + distancia (A[i],B[j]);  
				         d2 =d[i-1][j-1] +2*distancia (A[i],B[j]);
				         d3 =d[i][j-1]   + distancia (A[i],B[j]); 
				         
			    					}
			    				
			    	if(i==1&&j>1){
		    	
			    	     d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);
				         d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
				         d3 =d[i-1][j]   + distancia (A[i],B[j]); 
				         
			    	        }					
					if(i>1&&j==1){
			    	     d1 =d[i][j-1]  +  distancia (A[i],B[j]); 
				         d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
				         d3 =d[i-2][j-1]   + distancia (A[i-1],B[j])+2*distancia (A[i-1],B[j])+distancia(A[i],B[j]);
			    	
			    	        }
				
				
					if(i!=0||j!=0){
					
	//				System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
					d[i][j]= min(d1,d2,d3);
	//				System.out.println("d["+i+"]["+j+"]"+d[i][j]);
					d1=1000000;
					d2=1000000;
					d3=1000000;
					
					}					
				    
	//			    System.out.println("d["+i+"]["+j+"] = "+d[i][j]+" min ( d1 = "+d1+" d2 = "+d2+" d3 = "+d3+")");
				    suma=suma+d[i][j];
				    
					/*************/
					
						
						}//del if
					
				 }//de if
				
				}	//del else
			
			
			}while(true);
		
		/****************/
		
/*		for (i=0;i<A.length;i++ ){
			for(j=0;j<B.length;j++){
				
	//			if (i!=0&&j!=0&&j!=1){System.out.println("entroooo if (i!=0&&j!=0&&j!=1)");	d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);}
	//			if (i!=0&&i!=1&&j!=0&&j!=1){ System.out.println("entroooo if (i!=0&&j!=0)");         d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  }
	//			if (i!=0&&i!=1&&j!=0){  System.out.println("entroooo if (i!=0&&i!=1&&j!=0)");        d3 =d[i-2][j-1]   + 2*distancia (A[i-1],B[j])+distancia(A[i],B[j]); }
				
				if(i>=2&&j>=2){
//					System.out.println("entroooo if i>=2&&j>=2");
				  	     d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);
	                     d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
	            		 d3 =d[i-2][j-1]   + 2*distancia (A[i-1],B[j])+distancia(A[i],B[j]);
				}
				
				// si estan en los bordes
				if (i==0&&j!=0){    d1 =d[i][j-1]   + distancia (A[i],B[j]); }
				if (i!=0&&j==0){    d3 =d[i-1][j]   + distancia (A[i],B[j]);  }
				
			    if (i==1&&j==1){
	//		    	System.out.println("entroooo if (i==1&&j==1)");
				                    d1 =d[i-1][j]   + distancia (A[i],B[j]);  
				                    d2 =d[i-1][j-1] +2*distancia (A[i],B[j]);
				                    d3 =d[i][j-1]   + distancia (A[i],B[j]); 
				                    
				    
			    					}
			    				
			    if(i==1&&j>1){
	//		    	System.out.println("entroooo if(i==1&&j>1)");
			    	
			    	                 d1 =d[i-1][j-2]  +  2*distancia (A[i],B[j-1])+distancia(A[i],B[j]);
				                     d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
				                     d3 =d[i-1][j]   + distancia (A[i],B[j]); 
				                     
				             //        System.out.println("d[0][2]"+d[0][2]);
	//		    	System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
			    	        }					
			    					
				
				if(i>1&&j==1){
			    	                 d1 =d[i][j-1]  +  distancia (A[i],B[j]); 
				                     d2 =d[i-1][j-1]   + 2*distancia (A[i],B[j]);  
				                     d3 =d[i-2][j-1]   + distancia (A[i-1],B[j])+2*distancia (A[i-1],B[j])+distancia(A[i],B[j]);
			    	
			    	        }
				
				
				if(i!=0||j!=0){
					
	//				System.out.println("d1 d2 d3 "+d1+" "+d2+" "+d3+" end");                
					d[i][j]= min(d1,d2,d3);
	//				System.out.println("d["+i+"]["+j+"]"+d[i][j]);
					d1=1000000;
					d2=1000000;
					d3=1000000;
					
					}					
//				 else {System.out.println("no entro en "+i+" "+j);}   
				    
				    
	//			    System.out.println("d["+i+"]["+j+"] = "+d[i][j]+" min ( d1 = "+d1+" d2 = "+d2+" d3 = "+d3+")");
				    suma=suma+d[i][j];
				    
				
				} //del for
				
	//			System.out.println(" ");
			
			}// del for
	*/	
		
//		 System.out.println(" distancia sakoe and chiba simetrico p=1"+suma);
//		 System.out.println("                  normalizada= "+suma/(A.length+B.length));
//		return suma/(A.length+B.length);
		}	
	// saca la distacia euclediana entre dos vectores del mismo tamaño	
	public  double distancia ( double [] A, double [] B )	{
	
	if(opcion==1) {return distanciaEuclidea (A,B);}
	if(opcion==2) {return distanciaChebyshev(A,B);}
	
	return 0.0;
 	}
	
	
	public  double distanciaEuclidea ( double [] A, double [] B )	{
	
	
	int i;
	int N=A.length;
	double suma=0,distancia=0;
	for (i=0;i<N;i++){
		suma=suma+Math.pow((A[i]-B[i]),2);
		}
 		
 		distancia=Math.sqrt(suma); 
 		
// 		System.out.print(" distancia "+distancia);
 	return distancia;
 	}
	

	public  double distanciaChebyshev(double [] A, double [] B ){
		
	
	int i;
	int N=A.length;
	double suma=0,distancia=0;
	for (i=0;i<N;i++){
		suma=Math.abs((A[i]-B[i]));
		if(distancia<suma){
			distancia=suma;
			
			}
		
		}
 		
// 		System.out.print(" distancia "+distancia);
 	return distancia;	
		}
	
	
	
	
	private  double min(double A, double B, double C){
		
		if (A<=B&&A<=C){return A;}
		if (B<=A&&B<=C){return B;}
		if (C<=A&&C<=B){return C;}
		
		
		return 0;
		}
		
		}