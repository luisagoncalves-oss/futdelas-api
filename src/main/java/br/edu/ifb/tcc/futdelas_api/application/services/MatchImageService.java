package br.edu.ifb.tcc.futdelas_api.application.services;

import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.presentation.controller.dto.MatchData;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class MatchImageService {

    public Mono<byte[]> generateImage(MatchData match) {
        return Mono.fromSupplier(() -> {
            try {
                int width = 700, height = 450;
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fundo
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                // Header Roxo
                g.setColor(new Color(106, 13, 173));
                g.fillRect(0, 0, width, 70);

                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", Font.BOLD, 26));
                g.drawString("⚽ FutDelas", 20, 45);

                g.setFont(new Font("SansSerif", Font.PLAIN, 20));
                g.drawString(match.tournament, 200, 45);

                // Caixa central para placar
                drawRoundedBox(g, width / 2 - 80, 140, 160, 90, new Color(106, 13, 173));
                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", Font.BOLD, 40));
                drawCenteredText(g, match.homeScore + " - " + match.awayScore,
                        width / 2 - 80, 140, 160, 90);

                // Caixa do time da casa
                drawTeamBox(g, match.homeTeam, match.homeTeamColor, 80, 140, 180, 90);

                // Caixa do time visitante
                drawTeamBox(g, match.awayTeam, match.awayTeamColor, width - 260, 140, 180, 90);

                // Status e Data
                g.setFont(new Font("SansSerif", Font.PLAIN, 18));
                g.setColor(Color.DARK_GRAY);
                g.drawString("Status: " + match.status, 80, 300);

                String date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        .withZone(ZoneId.of("America/Sao_Paulo"))
                        .format(Instant.ofEpochSecond(match.startTimestamp));
                g.drawString("Data: " + date, 80, 330);

                // Rodapé
                g.setColor(new Color(240, 240, 240));
                g.fillRect(0, height - 50, width, 50);
                g.setColor(new Color(106, 13, 173));
                g.setFont(new Font("SansSerif", Font.BOLD, 16));
                g.drawString("FutDelas © 2025", width / 2 - 60, height - 20);

                g.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                return baos.toByteArray();

            } catch (Exception e) {
                throw new RuntimeException("Erro ao gerar imagem", e);
            }
        });
    }

    private void drawRoundedBox(Graphics2D g, int x, int y, int w, int h, Color bgColor) {
        g.setColor(bgColor);
        g.fill(new RoundRectangle2D.Float(x, y, w, h, 20, 20));
    }

    private void drawCenteredText(Graphics2D g, String text, int x, int y, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (w - fm.stringWidth(text)) / 2;
        int textY = y + ((h - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(text, textX, textY);
    }

    private void drawTeamBox(Graphics2D g, String teamName, String colorHex, int x, int y, int w, int h) {
        Color color = Color.decode(colorHex);
        drawRoundedBox(g, x, y, w, h, color);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        drawCenteredText(g, teamName, x, y, w, h);
    }
}