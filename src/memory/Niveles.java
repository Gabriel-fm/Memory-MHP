/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  MEMORY-MHP - The game of Memory for MHP platforms						 *
 *  Copyright (C) 2008  Gabriel Franco (find.the.error@gmail.com)				 *
 *																		 	 *
 *  This program is free software; you can redistribute it and/or modify	 *
 *  it under the terms of the GNU General Public License as published by	 *
 *  the Free Software Foundation; either version 2 of the License, or		 *
 *  (at your option) any later version. See LICENSE document for details. 	 *
 *																	     	 *
 *  This program is distributed in the hope that it will be useful, 		 *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 		     *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   		 *
 *  GNU General Public License for more details.							 *
 *																		     *
 *  You should have received a copy of the GNU General Public License along  *
 *  with this program; if not, write to the Free Software Foundation, Inc.,  *
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.			     *
 *             															     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package memory;

import java.awt.Image;
import java.awt.event.ActionEvent;

import org.dvb.ui.DVBTextLayoutManager;
import org.havi.ui.HContainer;
import org.havi.ui.HIcon;
import org.havi.ui.HNavigable;
import org.havi.ui.HText;
import org.havi.ui.HToggleButton;
import org.havi.ui.event.HActionListener;

public class Niveles implements HActionListener {

	int hor, ver;
	int contador = 0;
	int tiempoError = 800;
	int nivel, tiempo;
	int elegido1, elegido2, estadoelegido = 0;
	HIcon[] cartasOcultas;
	HToggleButton[] todasCartas;
	private int separacion_vertical = 0;
	private int separacion_horizontal = 0;
	Game g;
	private HToggleButton[][] htbpantalla;

	public Niveles(Game gam) {
		g = gam;

	}

	public void getNuevoNivel(int timer, int dificultad) {
		nivel = dificultad;
		tiempo = timer;

		if (nivel == 1) {
			// separacion_horizontal = 20;
			// separacion_vertical = 20;
			colocarCartas(4, 3);
		} else if (nivel == 3) {
			// separacion_horizontal = 20;
			// separacion_vertical = 20;
			colocarCartas(5, 4);
		} else if (nivel == 2) {

			// separacion_horizontal = 28;
			// separacion_vertical = 25;
			colocarCartas(4, 4);
		}
		g.cont.setTiempo(tiempo);
		g.cont.start();
		

	}

	private void colocarCartas(int horizontal, int vertical) {

		hor = horizontal;
		ver = vertical;

		for (int i = 0; i < g.hc.length; i++) {
			g.hc[i].setVisible(false);
			g.hc[i] = null;
		}

		int numCartas = (horizontal * vertical);

		// Crear array de elementos
		Image[] listado = Cartas.getCartas(numCartas / 2);
		todasCartas = new HToggleButton[numCartas];

		for (int i = 0; i < numCartas / 2; i++) {
			todasCartas[i] = new HToggleButton();
			todasCartas[i].setGraphicContent(Cartas.reverso,
					HToggleButton.NORMAL_STATE);
			todasCartas[i].setGraphicContent(Cartas.reverso_foc,
					HToggleButton.FOCUSED_STATE);
			todasCartas[i].setGraphicContent(listado[i],
					HToggleButton.ACTIONED_STATE);
			todasCartas[i].setGraphicContent(listado[i],
					HToggleButton.ACTIONED_FOCUSED_STATE);
			todasCartas[i].setGraphicContent(listado[i],
					HToggleButton.DISABLED_ACTIONED_FOCUSED_STATE);
			todasCartas[i].setName(Integer.toString(i));
		}
		int contador = 0;

		for (int i = numCartas / 2; i < numCartas; i++) {
			todasCartas[i] = new HToggleButton();
			todasCartas[i].setGraphicContent(Cartas.reverso,
					HToggleButton.NORMAL_STATE);
			todasCartas[i].setGraphicContent(Cartas.reverso_foc,
					HToggleButton.FOCUSED_STATE);
			todasCartas[i].setGraphicContent(Cartas.reverso_foc,
					HToggleButton.DISABLED_FOCUSED_STATE);
			todasCartas[i].setGraphicContent(listado[contador],
					HToggleButton.ACTIONED_STATE);
			todasCartas[i].setGraphicContent(listado[contador],
					HToggleButton.ACTIONED_FOCUSED_STATE);
			todasCartas[i].setGraphicContent(listado[contador],
					HToggleButton.DISABLED_ACTIONED_FOCUSED_STATE);
			todasCartas[i].setName(Integer.toString(contador));
			contador++;

		}

		g.superficie = new HContainer(25, 25, 465, 530);
		Game.escena.add(g.superficie);
		g.superficie.setVisible(true);
		
		

		// Encontrar distancias de colocacion
		// altura = Game.superficie.getHeight();
		// anchura = g.superficie.getWidth();

		if (separacion_vertical == 0 | separacion_horizontal == 0) {
			separacion_vertical = (g.superficie.getHeight() - (vertical * Cartas.reverso
					.getHeight(null)))
					/ (vertical + 1);

			separacion_horizontal = (g.superficie.getWidth() - (horizontal * Cartas.reverso
					.getWidth(null)))
					/ (horizontal + 1);
		}

		// Array de colocacion de elementos
		htbpantalla = new HToggleButton[horizontal][vertical];
		shuffle(todasCartas);
		for (int i = 0; i < htbpantalla.length; i++) {
			for (int j = 0; j < htbpantalla[i].length; j++) {

				htbpantalla[i][j] = todasCartas[(i * htbpantalla[i].length) + j];
				htbpantalla[i][j]
						.setBounds(
								(separacion_horizontal + (separacion_horizontal + htbpantalla[i][j]
										.getGraphicContent(
												HToggleButton.NORMAL_STATE)
										.getHeight(null))
										* i),
								(separacion_vertical + (separacion_vertical + htbpantalla[i][j]
										.getGraphicContent(
												HToggleButton.NORMAL_STATE)
										.getWidth(null))
										* j),
								Cartas.reverso.getWidth(null) + 4,
								Cartas.reverso.getHeight(null) + 4);
				g.superficie.add(htbpantalla[i][j]);
				htbpantalla[i][j].setVisible(true);
				htbpantalla[i][j].setActionCommand(Integer
						.toString((i * htbpantalla[i].length) + j));
				htbpantalla[i][j].addHActionListener(this);
			}
		}
		for (int i = 0; i < htbpantalla.length; i++) {
			for (int j = 0; j < htbpantalla[i].length; j++) {

				HNavigable hnup = null;
				HNavigable hndown = null;
				HNavigable hnleft = null;
				HNavigable hnright = null;

				if (i == 0) {
					hnright = htbpantalla[i + 1][j];
				} else if (i == htbpantalla.length - 1) {
					hnleft = htbpantalla[i - 1][j];
				} else {
					hnleft = htbpantalla[i - 1][j];
					hnright = htbpantalla[i + 1][j];
				}

				if (j == 0) {
					hndown = htbpantalla[i][j + 1];
				} else if (j == htbpantalla[i].length - 1) {
					hnup = htbpantalla[i][j - 1];
				} else {
					hnup = htbpantalla[i][j - 1];
					hndown = htbpantalla[i][j + 1];
				}

				htbpantalla[i][j].setFocusTraversal(hnup, hndown, hnleft,
						hnright);

			}
		}
		Game.escena.popToFront(g.superficie);
		g.superficie.repaint();
		Game.escena.repaint();
		htbpantalla[0][0].requestFocus();


	}

	private void shuffle(HToggleButton[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int r = i + (int) (Math.random() * (N - i)); // entre i y N-1
			exch(a, i, r);
		}
	}

	private void exch(HToggleButton[] a, int i, int j) {
		HToggleButton swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	public void actionPerformed(ActionEvent aev) {

		if (estadoelegido == 0) {
			estadoelegido = 1;
			elegido1 = Integer.parseInt(aev.getActionCommand());
		} else {
			elegido2 = Integer.parseInt(aev.getActionCommand());
			comprobacion();
			estadoelegido = 0;
		}

	}

	public void comprobacion() {
		Thread volver = null;
		if (elegido1 != elegido2) {
			if (todasCartas[elegido1].getName().equals(
					todasCartas[elegido2].getName())) {
				todasCartas[elegido1].setEnabled(false);
				todasCartas[elegido2].setEnabled(false);
				
				estadoelegido = 0;
				contador++;
				if (contador == (hor * ver) / 2) {
					todasCartas[elegido1].setSwitchableState(false);
					todasCartas[elegido2].setSwitchableState(false);

					Image im2 = todasCartas[elegido2]
							.getGraphicContent(HToggleButton.ACTIONED_STATE);
					HIcon hi2 = new HIcon(im2);
					hi2.setBounds(todasCartas[elegido2].getX(),
							todasCartas[elegido2].getY(),
							im2.getWidth(null) + 4, im2.getHeight(null) + 4);
					g.superficie.add(hi2);
					hi2.setVisible(true);
					g.superficie.popToFront(hi2);

					Image im1 = todasCartas[elegido1]
							.getGraphicContent(HToggleButton.ACTIONED_STATE);
					HIcon hi1 = new HIcon(im1);
					hi1.setBounds(todasCartas[elegido1].getX(),
							todasCartas[elegido1].getY(),
							im1.getWidth(null) + 4, im1.getHeight(null) + 4);
					g.superficie.add(hi1);
					hi1.setVisible(true);
					g.superficie.popToFront(hi1);

					int minutosGastados, segundosGastados;
					minutosGastados = Game.tiempo-g.cont.minutos+1;
					segundosGastados = 60 - g.cont.segundos;
					HText textoG = new HText("¡HAS GANADO!\n\n" +
							"Tiempo empleado:\n" +
							minutosGastados + " minutos " + segundosGastados + " segundos");
					DVBTextLayoutManager tlm = new DVBTextLayoutManager();
					tlm
							.setHorizontalAlign(DVBTextLayoutManager.HORIZONTAL_CENTER);
					textoG.setBackground(java.awt.Color.yellow);
					textoG.setBounds(Game.escena.getWidth() / 4, Game.escena
							.getHeight() / 4+120, Game.escena.getWidth() / 2,
							Game.escena.getHeight() / 4);
					textoG.setTextLayoutManager(tlm);
					textoG.setForeground(java.awt.Color.black);
					textoG.setFont(new java.awt.Font("Tiresias",
							HText.NORMAL_STATE, 30));
					textoG.setBackgroundMode(HText.BACKGROUND_FILL);
					Game.escena.add(textoG);
					Game.escena.popToFront(textoG);

					g.cont.Stop();
					
					volver = new Thread() {
						public void run() {
							try {
								Thread.sleep(15000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							g.configuracion();
						}
					};
					volver.start();
				}
				Sonidos.acierto();
			} else {

				todasCartas[elegido1].setSwitchableState(false);
				todasCartas[elegido2].setSwitchableState(false);

				Thread t = new Thread() {
					public void run() {
						Image im2 = todasCartas[elegido2]
								.getGraphicContent(HToggleButton.ACTIONED_STATE);
						HIcon hi2 = new HIcon(im2);
						hi2.setBounds(todasCartas[elegido2].getX(),
								todasCartas[elegido2].getY(), im2
										.getWidth(null) + 4, im2
										.getHeight(null) + 4);
						g.superficie.add(hi2);
						hi2.setVisible(true);
						g.superficie.popToFront(hi2);

						Image im1 = todasCartas[elegido1]
								.getGraphicContent(HToggleButton.ACTIONED_STATE);
						HIcon hi1 = new HIcon(im1);
						hi1.setBounds(todasCartas[elegido1].getX(),
								todasCartas[elegido1].getY(), im1
										.getWidth(null) + 4, im1
										.getHeight(null) + 4);
						g.superficie.add(hi1);
						hi1.setVisible(true);
						g.superficie.popToFront(hi1);

						try {
							sleep(tiempoError);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						g.superficie.remove(hi1);
						g.superficie.remove(hi2);
						hi1 = hi2 = null;
						im1 = im2 = null;
						g.superficie.repaint();

						Sonidos.error();

					}
				};
				t.start();

			}
		}
	}

	public void pasado() {

		

		// Sonido de perder
		Sonidos.sonido(null);

		
	}

	public void perdido() {
		HText textoP = new HText("LO SIENTO\nHAS PERDIDO");
		DVBTextLayoutManager tlm = new DVBTextLayoutManager();
		tlm.setHorizontalAlign(DVBTextLayoutManager.HORIZONTAL_CENTER);
		textoP.setBackground(java.awt.Color.yellow);
		textoP.setBounds(Game.escena.getWidth() / 4,
				Game.escena.getHeight() / 4, Game.escena.getWidth() / 2,
				Game.escena.getHeight() / 4);
		textoP.setTextLayoutManager(tlm);
		textoP.setForeground(java.awt.Color.black);
		textoP.setFont(new java.awt.Font("Tiresias", HText.NORMAL_STATE, 30));
		textoP.setBackgroundMode(HText.BACKGROUND_FILL);
		Game.escena.add(textoP);
		Game.escena.popToFront(textoP);
		g.cont.Stop();

		// Sonido de perder
		Sonidos.sonido(null);

		Thread volver = new Thread() {
			public void run() {
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				g.configuracion();
			}
		};
		volver.run();
	}

}
