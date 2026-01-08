package com.example.jocparelles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private Carta[] cartas = new Carta[20];
    private ImageView[] imageViews = new ImageView[20];
    private int primeraCarta = -1;
    private int segundaCarta = -1;
    private boolean bloqueado = false;

    private String jugador1 = "Jugador 1";
    private String jugador2 = "Jugador 2";
    private int puntuacionJ1 = 0;
    private int puntuacionJ2 = 0;
    private boolean turnoJ1 = true; // true = J1, false = J2
    private boolean juegoIniciado = false;

    private ArrayList<String> rankingPartidas = new ArrayList<>();
    private HashMap<String, Integer> rankingJugadores = new HashMap<>();

    private EditText etJugador1, etJugador2;
    private TextView tvPuntosJ1, tvPuntosJ2;
    private Button btnIniciar, btnGuardar, btnCargar;
    private ListView listRanking;
    private Ranking rankingAdapter;

    private int[] imagenesIds = {
            R.drawable.bufanda, R.drawable.caramelo, R.drawable.cascabeles,
            R.drawable.arbol, R.drawable.fuegos, R.drawable.gorro,
            R.drawable.guantes, R.drawable.hoguera, R.drawable.regalo,
            R.drawable.trineo
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarViews();
        inicializarCartas();
        cargarRanking();

        btnIniciar.setOnClickListener(v -> iniciarJuego());
        btnGuardar.setOnClickListener(v -> guardarPartida());
        btnCargar.setOnClickListener(v -> cargarPartida());
    }

    private void inicializarViews() {
        etJugador1 = findViewById(R.id.jugador1);
        etJugador2 = findViewById(R.id.jugador2);
        tvPuntosJ1 = findViewById(R.id.puntosJ1);
        tvPuntosJ2 = findViewById(R.id.puntosJ2);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCargar = findViewById(R.id.btnCargar);
        listRanking = findViewById(R.id.listRanking);

        for (int i = 0; i < 20; i++) {
            int resId = getResources().getIdentifier("card" + i, "id", getPackageName());
            imageViews[i] = findViewById(resId);
            final int index = i;
            imageViews[i].setOnClickListener(v -> voltearCarta(index));
        }

        ArrayList<String> rankingInicial = new ArrayList<>();
        rankingInicial.add("Aún no hay jugadores");
        rankingAdapter = new Ranking(this, rankingInicial);
        listRanking.setAdapter(rankingAdapter);

        actualizarListaRanking();
    }

    private void inicializarCartas() {
        ArrayList<Integer> imagenes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            imagenes.add(imagenesIds[i]);
            imagenes.add(imagenesIds[i]);
        }

        Collections.shuffle(imagenes);

        for (int i = 0; i < 20; i++) {
            cartas[i] = new Carta(i, imagenes.get(i));
        }
    }

    private void iniciarJuego() {
        jugador1 = etJugador1.getText().toString().isEmpty() ? "Jugador 1" : etJugador1.getText().toString();
        jugador2 = etJugador2.getText().toString().isEmpty() ? "Jugador 2" : etJugador2.getText().toString();

        puntuacionJ1 = 0;
        puntuacionJ2 = 0;
        turnoJ1 = true;
        juegoIniciado = true;

        actualizarPuntuaciones();

        for (Carta carta : cartas) {
            carta.setVolteada(false);
            carta.setEmparejada(false);
        }

        ArrayList<Integer> imagenes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            imagenes.add(imagenesIds[i]);
            imagenes.add(imagenesIds[i]);
        }
        Collections.shuffle(imagenes);

        for (int i = 0; i < 20; i++) {
            cartas[i] = new Carta(i, imagenes.get(i));
            imageViews[i].setImageResource(R.drawable.card_background);
        }

        Toast.makeText(this, "¡Juego iniciado! Turno de: " + jugador1, Toast.LENGTH_SHORT).show();
    }

    private void voltearCarta(int index) {
        if (!juegoIniciado || bloqueado || cartas[index].isVolteada() || cartas[index].isEmparejada()) {
            return;
        }

        cartas[index].setVolteada(true);
        imageViews[index].setImageResource(cartas[index].getImagenId());

        if (primeraCarta == -1) {
            primeraCarta = index;
        } else {
            segundaCarta = index;
            bloqueado = true;

            new android.os.Handler().postDelayed(() -> {
                comprobarPareja();
                bloqueado = false;
            }, 1000);
        }
    }

    private void comprobarPareja() {
        if (cartas[primeraCarta].getImagenId() == cartas[segundaCarta].getImagenId()) {
            cartas[primeraCarta].setEmparejada(true);
            cartas[segundaCarta].setEmparejada(true);

            if (turnoJ1) {
                puntuacionJ1++;
            } else {
                puntuacionJ2++;
            }

            actualizarPuntuaciones();

            if (juegoTerminado()) {
                finalizarJuego();
            }
        } else {
            cartas[primeraCarta].setVolteada(false);
            cartas[segundaCarta].setVolteada(false);
            imageViews[primeraCarta].setImageResource(R.drawable.card_background);
            imageViews[segundaCarta].setImageResource(R.drawable.card_background);

            turnoJ1 = !turnoJ1;
            String jugadorActual = turnoJ1 ? jugador1 : jugador2;
            Toast.makeText(this, "Turno de: " + jugadorActual, Toast.LENGTH_SHORT).show();
        }

        primeraCarta = -1;
        segundaCarta = -1;
    }

    private boolean juegoTerminado() {
        for (Carta carta : cartas) {
            if (!carta.isEmparejada()) {
                return false;
            }
        }
        return true;
    }

    private void finalizarJuego() {
        juegoIniciado = false;

        String mensaje;
        if (puntuacionJ1 > puntuacionJ2) {
            mensaje = "¡" + jugador1 + " gana con " + puntuacionJ1 + " puntos!";
            actualizarRankingJugador(jugador1, puntuacionJ1);

            if (puntuacionJ2 > 0) {
                actualizarRankingJugador(jugador2, puntuacionJ2);
            }
        } else if (puntuacionJ2 > puntuacionJ1) {
            mensaje = "¡" + jugador2 + " gana con " + puntuacionJ2 + " puntos!";
            actualizarRankingJugador(jugador2, puntuacionJ2);
            if (puntuacionJ1 > 0) {
                actualizarRankingJugador(jugador1, puntuacionJ1);
            }
        } else {
            mensaje = "¡Empate! Ambos con " + puntuacionJ1 + " puntos";
            actualizarRankingJugador(jugador1, puntuacionJ1);
            actualizarRankingJugador(jugador2, puntuacionJ2);
        }

        String resultadoPartida = jugador1 + ": " + puntuacionJ1 + " - " + jugador2 + ": " + puntuacionJ2;
        rankingPartidas.add(0, resultadoPartida);

        if (rankingPartidas.size() > 10) {
            rankingPartidas = new ArrayList<>(rankingPartidas.subList(0, 10));
        }

        actualizarListaRanking();

        guardarRanking();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Juego terminado!")
                .setMessage(mensaje)
                .setPositiveButton("OK", null)
                .show();
    }

    private void actualizarRankingJugador(String nombreJugador, int puntos) {
        if (rankingJugadores.containsKey(nombreJugador)) {
            int puntajeActual = rankingJugadores.get(nombreJugador);
            if (puntos > puntajeActual) {
                rankingJugadores.put(nombreJugador, puntos);
            }
        } else {
            rankingJugadores.put(nombreJugador, puntos);
        }
    }

    private void actualizarListaRanking() {
        if (rankingJugadores.isEmpty()) {
            ArrayList<String> listaVacia = new ArrayList<>();
            listaVacia.add("Aún no hay jugadores");
            rankingAdapter.clear();
            rankingAdapter.addAll(listaVacia);
            rankingAdapter.notifyDataSetChanged();
            return;
        }

        ArrayList<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(rankingJugadores.entrySet());

        Collections.sort(listaOrdenada, (a, b) -> b.getValue().compareTo(a.getValue()));

        ArrayList<String> rankingParaMostrar = new ArrayList<>();

        for (int i = 0; i < Math.min(listaOrdenada.size(), 10); i++) {
            Map.Entry<String, Integer> entry = listaOrdenada.get(i);
            String posicion = (i + 1) + ". " + entry.getKey() + ": " + entry.getValue() + " pts";
            rankingParaMostrar.add(posicion);
        }

        rankingAdapter.clear();
        rankingAdapter.addAll(rankingParaMostrar);
        rankingAdapter.notifyDataSetChanged();
    }

    private void actualizarPuntuaciones() {
        tvPuntosJ1.setText(jugador1 + ": " + puntuacionJ1);
        tvPuntosJ2.setText(jugador2 + ": " + puntuacionJ2);
    }

    private void cargarRanking() {
        SharedPreferences prefs = getSharedPreferences("JocParelles", MODE_PRIVATE);

        String jsonPartidas = prefs.getString("rankingPartidas", "[]");
        Gson gson = new Gson();
        Type typePartidas = new TypeToken<ArrayList<String>>(){}.getType();
        rankingPartidas = gson.fromJson(jsonPartidas, typePartidas);

        if (rankingPartidas == null) {
            rankingPartidas = new ArrayList<>();
        }

        String jsonJugadores = prefs.getString("rankingJugadores", "{}");
        Type typeJugadores = new TypeToken<HashMap<String, Integer>>(){}.getType();
        rankingJugadores = gson.fromJson(jsonJugadores, typeJugadores);

        if (rankingJugadores == null) {
            rankingJugadores = new HashMap<>();
        }
    }

    private void guardarRanking() {
        SharedPreferences prefs = getSharedPreferences("JocParelles", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();

        String jsonPartidas = gson.toJson(rankingPartidas);
        editor.putString("rankingPartidas", jsonPartidas);

        String jsonJugadores = gson.toJson(rankingJugadores);
        editor.putString("rankingJugadores", jsonJugadores);

        editor.apply();
    }

    private void guardarPartida() {
        if (!juegoIniciado) {
            Toast.makeText(this, "No hay partida en curso", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("JocParelles", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("jugador1", jugador1);
        editor.putString("jugador2", jugador2);
        editor.putInt("puntuacionJ1", puntuacionJ1);
        editor.putInt("puntuacionJ2", puntuacionJ2);
        editor.putBoolean("turnoJ1", turnoJ1);
        editor.putBoolean("juegoIniciado", juegoIniciado);

        ArrayList<Integer> cartasGuardadas = new ArrayList<>();
        for (Carta carta : cartas) {
            cartasGuardadas.add(carta.getImagenId());
            cartasGuardadas.add(carta.isVolteada() ? 1 : 0);
            cartasGuardadas.add(carta.isEmparejada() ? 1 : 0);
        }

        Gson gson = new Gson();
        String jsonCartas = gson.toJson(cartasGuardadas);
        editor.putString("cartas", jsonCartas);

        editor.apply();
        Toast.makeText(this, "Partida guardada", Toast.LENGTH_SHORT).show();
    }

    private void cargarPartida() {
        SharedPreferences prefs = getSharedPreferences("JocParelles", MODE_PRIVATE);

        jugador1 = prefs.getString("jugador1", "Jugador 1");
        jugador2 = prefs.getString("jugador2", "Jugador 2");
        puntuacionJ1 = prefs.getInt("puntuacionJ1", 0);
        puntuacionJ2 = prefs.getInt("puntuacionJ2", 0);
        turnoJ1 = prefs.getBoolean("turnoJ1", true);
        juegoIniciado = prefs.getBoolean("juegoIniciado", false);

        etJugador1.setText(jugador1);
        etJugador2.setText(jugador2);
        actualizarPuntuaciones();

        String jsonCartas = prefs.getString("cartas", "");
        if (!jsonCartas.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
            ArrayList<Integer> cartasGuardadas = gson.fromJson(jsonCartas, type);

            if (cartasGuardadas != null && cartasGuardadas.size() == 60) {
                for (int i = 0; i < 20; i++) {
                    int imagenId = cartasGuardadas.get(i * 3);
                    boolean volteada = cartasGuardadas.get(i * 3 + 1) == 1;
                    boolean emparejada = cartasGuardadas.get(i * 3 + 2) == 1;

                    cartas[i] = new Carta(i, imagenId);
                    cartas[i].setVolteada(volteada);
                    cartas[i].setEmparejada(emparejada);

                    if (emparejada || volteada) {
                        imageViews[i].setImageResource(imagenId);
                    } else {
                        imageViews[i].setImageResource(R.drawable.card_background);
                    }
                }
            }
        }

        Toast.makeText(this, "Partida cargada", Toast.LENGTH_SHORT).show();
    }
}
