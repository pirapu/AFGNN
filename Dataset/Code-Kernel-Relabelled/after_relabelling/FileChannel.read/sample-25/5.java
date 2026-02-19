public class func{
public void seek(final File seekFile,long startByte){
                if (bb.remaining() <= MIN_BUFFER_REMAINING_REQUIRED) {
                    bb.clear();
                    fc.position(filePointerCount);
                    fc.read(bb, fc.position());
                    bb.flip();
                    if (bb.limit() <= MIN_BUFFER_REMAINING_REQUIRED) {
                        return false;
                    }
                }
                if (MPEGFrameHeader.isMPEGFrame(bb)) {
                    try {
                        mp3FrameHeader = MPEGFrameHeader.parseMPEGHeader(bb);
                        syncFound = true;
                        if (XingFrame.isXingFrame(bb, mp3FrameHeader)) {
                            try {
                                mp3XingFrame = XingFrame.parseXingFrame();
                            } catch (InvalidAudioFrameException ex) {
                            }
                            break;
                        } else if (VbriFrame.isVbriFrame(bb, mp3FrameHeader)) {
                            try {
                                mp3VbriFrame = VbriFrame.parseVBRIFrame();
                            } catch (InvalidAudioFrameException ex) {
                            }
                            break;
                        }
                        else {
                            syncFound = isNextFrameValid(seekFile, filePointerCount, bb, fc);
                            if (syncFound) {
                                break;
                            }
                        }
                    } catch (InvalidAudioFrameException ex) {
                    }
                }
                bb.position(bb.position() + 1);
}
}
