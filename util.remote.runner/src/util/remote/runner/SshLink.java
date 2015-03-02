package util.remote.runner;

import java.io.IOException;
import java.security.PublicKey;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SshLink {

	private SSHClient ssh;
	private Config config;
	
	public SshLink(Config config) {
		this.config = config;
		init();
	}
	
	public void run() {
		try {			
			init();
			login();
			killOldApp();
			upload();
//			if (config.isGuiApp) startNewGuiApp();
//			else startNewConsoleApp();
			startShellScript();
			dumpLog();
			logout();
			System.out.println("Done.");
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private void init() {
		System.out.println("Starting SSH client...");
		ssh = new SSHClient();
		ssh.addHostKeyVerifier(  
			    new HostKeyVerifier() {  
					@Override
					public boolean verify(String hostname, int port, PublicKey key) {
						return true;  // don't bother verifying
					}  
			    }  
			);
	}
	
	private void login() throws Exception {
		System.out.println("Logging in...");
		ssh.connect(config.remoteip, config.remoteport);
		ssh.authPassword(config.remoteun, config.remotepw);
	}
	
	private String execWatch(String cmdStr) throws Exception {
		String res = null;
        final Session session = ssh.startSession();
        try {
            final Command cmd = session.exec(cmdStr);
            res = IOUtils.readFully(cmd.getInputStream()).toString();
//            cmd.join(5, TimeUnit.SECONDS);
 //           log.info("** exit status: " + cmd.getExitStatus());
        } finally {
            session.close();
        }
        return res;
	}
	
	private void exec(String cmdStr) throws Exception {
        final Session session = ssh.startSession();
        try {
            session.exec(cmdStr);
        } finally {
            session.close();
        }
	}
	
	
	private void killOldApp() throws Exception {
		String res = execWatch("ps -fu"+config.remoteun+" | grep \"[[:digit:]] java -jar "+config.jarname+"\"");
		if (res.contains(config.jarname)) {
//			System.out.println(res);
			System.out.println("Detected app running - it will now be shut down");
			int firstSpacePos = res.indexOf(" ");
			String psnum = res.substring(firstSpacePos, res.indexOf(" ", firstSpacePos+4)).trim();
			System.out.println("Killing process '"+psnum+"'");
			exec("kill "+psnum);
		}
		Thread.sleep(2000);
	}
	
	private void upload() throws IOException {
		System.out.println("Uploading new app...");
        final SFTPClient sftp = ssh.newSFTPClient();
        try {
            sftp.put(new FileSystemFile(config.deploydir+"/"+config.jarname), config.remotedir);
        } finally {
            sftp.close();
        }
	}
	
	private void dumpLog() throws Exception {
		System.out.println("Will dump log file in five seconds...");
		Thread.sleep(5000);
		String res;
		res = execWatch("cat runremote.log");
		System.out.println("--- runremote.log begin ---");
		System.out.println(res);
		System.out.println("--- runremote.log end ---");
	}

	private void startShellScript() throws Exception {
		System.out.println("Starting 'runremote.sh'");
		exec("./runremote.sh&");
	}

	
	
	private void startNewGuiApp() throws Exception {
	
//		String res;
//		res = execWatch("./runremote.sh&");
//		System.out.println("runremote.sh returned: '"+res+"'");
		
//		res = execWatch("pwd");
//		System.out.println("'"+res+"'");
		
		
//		res = execWatch("env DISPLAY=:0 java -jar "+config.jarname+"&");
//		System.out.println("'"+res+"'");
//		execWatch("sudo ttyecho -n /dev/pts/0 env DISPLAY=:0 java -jar "+config.jarname);
		//  NOTE:  as root, exec:  cp /home/pi/.Xauthority /root
		// AND compile ttyecho.c and copy to bin path
	}

	private void startNewConsoleApp() throws Exception {
		System.out.println("Starting new console app...");
		exec("cd "+config.remotedir);
		exec("sudo ttyecho -n /dev/pts/0 java -jar "+config.jarname);
	}
	
	private void logout() throws Exception {
		System.out.println("Logging out.");
		ssh.close();
	}
	
}
