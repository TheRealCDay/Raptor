/**
 * New BSD License
 * http://www.opensource.org/licenses/bsd-license.php
 * Copyright 2009-2016 RaptorProject (https://github.com/Raptor-Fics-Interface/Raptor)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the RaptorProject nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package raptor.chess.pgn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StreamingPgnParser extends SimplePgnParser {
	int charsParsed = 0;
	int maxChars = -1;
	private File file;
	private BufferedReader reader;

	public StreamingPgnParser(File file, int maxChars) throws IOException {
		super("garbage");
		this.file = file;
		initReader();
		this.maxChars = maxChars;
	}

	public void jumpToLine(int lineNumber) {
		initReader();
		while (this.lineNumber  < (lineNumber-1)) {
			readNextLine();
		}
	}

	@Override
	protected void readNextLine() {
		try {
			currentLine = reader.readLine();
			if (currentLine != null) {
				charsParsed += currentLine.length();
				if (charsParsed > maxChars) {
					currentLine = null;
				}
				lineNumber++;
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void initReader() {
		try {
			if (reader != null) {
				reader.close();
				reader = null;
			}
			charsParsed = 0;
			lineNumber = 0;

			reader = new BufferedReader(new FileReader(file), 5000);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void close() {
		try {
			if (reader != null) {
				reader.close();
				reader = null;
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

	}
}
