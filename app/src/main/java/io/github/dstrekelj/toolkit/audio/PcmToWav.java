package io.github.dstrekelj.toolkit.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * TODO: Comment.
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
            wav.write(chunkId);                         // 4 B  Chunk ID
            wav.write((int) chunkSize);                 // 4 B  Chunk size
            wav.write(format);                          // 4 B  Format
            // "fmt " sub-chunk
            wav.write(subChunk1Id);                     // 4 B  Sub-chunk 1 ID
            wav.write(subChunk1Size);                   // 4 B  Sub-chunk 1 size
            wav.write(audioFormat);                     // 2 B  Audio format
            wav.write(numChannels);                     // 2 B  Number of channels
            wav.write(sampleRate);                      // 4 B  Sample rate
            wav.write(sampleRate * bytesPerChannel);    // 4 B  Byte rate
            wav.write(bytesPerChannel);                 // 2 B  Block align
            wav.write(bitsPerSample);                   // 2 B  Bits per sample
            // "data" sub-chunk
            wav.write(subChunk2Id);                     // 4 B  Sub-chunk 2 ID
            wav.write(subChunk2Size);                   // 4 B  Sub-chunk 2 size
            wav.write(pcmData);                         // * B  Sound data
            wav.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wav.toByteArray();
    }
}
