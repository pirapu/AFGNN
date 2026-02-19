public class func{
public void readValuesFromTile(int tileColumn,int tileRow,RasterRect fRect,FileChannel channel,ByteBuffer resultBuffer){
            for ( int row = bufferOffsetY; row < ( bufferOffsetY + inter.height ); ++row, ++currentIntersectRow ) {
                currentPos = ( bufferOffsetX + ( fRect.width * row ) ) * sampleSize;
                limit = currentPos + lineSize;
                resultBuffer.limit( limit );
                resultBuffer.position( currentPos );
                filePosition = filePos + ( ( tileOffsetX + ( currentIntersectRow * tileRect.width ) ) * sampleSize );
                channel.position( filePosition );
                channel.read( resultBuffer );
            }
}
}
