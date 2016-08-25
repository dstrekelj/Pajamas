package io.github.dstrekelj.toolkit.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Helper class for formatting PCM byte data to WAV byte data.
 */
public class PcmToWav {
    // http://soundfile.sapp.org/doc/WaveFormat/
    public static byte[] write(byte[] pcmData, byte numberOfChannels, int sampleRate, byte bitsPerSample) {
        byte[] chunkId = "RIFF".getBytes();
        // 36 because of subChunk1 (format) data
        long chunkSize = 36 + pcmData.length;
        byte[] format = "WAVE".getBytes();
        byte[] subChunk1Id = "fmt ".getBytes();
        byte[] subChunk1Size = {16, 0, 0, 0};
        byte[] audioFormat = {1, 0};
        byte[] numChannels = {numberOfChannels, 0};
        int bytesPerChannel = numberOfChannels * (bitsPerSample / 8);
        byte[] subChunk2Id = "data".getBytes();
        int subChunk2Size = pcmData.length;

        ByteArrayOutputStream wav = null;
        try {
            wav = new ByteArrayOutputStream();
            // "RIFF" header
            wav.write(chunkId);                             // 4 B  Chunk ID
            writeInt(wav, (int) chunkSize);                 // 4 B  Chunk size
            wav.write(format);                              // 4 B  Format
            // "fmt " sub-chunk
            wav.write(subChunk1Id);                         // 4 B  Sub-chunk 1 ID
            wav.write(subChunk1Size);                       // 4 B  Sub-chunk 1 size
            wav.write(audioFormat);                         // 2 B  Audio format
            wav.write(numChannels);                         // 2 B  Number of channels
            writeInt(wav, sampleRate);                      // 4 B  Sample rate
            writeInt(wav, sampleRate * bytesPerChannel);    // 4 B  Byte rate
            writeShort(wav, (short)bytesPerChannel);        // 2 B  Block align
            writeShort(wav, bitsPerSample);                 // 2 B  Bits per sample
            // "data" sub-chunk
            wav.write(subChunk2Id);                         // 4 B  Sub-chunk 2 ID
            writeInt(wav, subChunk2Size);                   // 4 B  Sub-chunk 2 size
            // Sound data
            for (int i = 0; i < pcmData.length; i += 2) {
                wav.write(pcmData[i + 1]);
                wav.write(pcmData[i]);
            }
            wav.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wav.toByteArray();
    }

    private static void writeInt(ByteArrayOutputStream out, int value) throws IOException {
        out.write(value);
        out.write(value >> 8);
        out.write(value >> 16);
        out.write(value >> 24);
    }

    private static void writeShort(ByteArrayOutputStream out, short value) throws IOException {
        out.write(value);
        out.write(value >> 8);
    }
}
